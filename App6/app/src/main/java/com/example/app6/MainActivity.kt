package com.example.app6

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Inicializar()
        Eventos()
    }


    fun Inicializar() {

        //Llenar Spinner
        val edocivil = arrayOf("Soltero", "Casado", "Divorciado")

        val adapter = ArrayAdapter(this,
            R.layout.ejemplo_spinner, //android.R.layout.simple_spinner_item
            edocivil)

        ListaEdoCivil.adapter = adapter
    }


    fun Eventos() {
        btnAceptar.setOnClickListener{ view->
                var informacion = ""
                informacion = String.format("Tu nombre es %s y tu estado civil es %s", txtNombre.text,
                    ListaEdoCivil.selectedItem.toString())
                lblInformacion.text = informacion
            }



        ListaEdoCivil.onItemSelectedListener  =  object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
                lblInformacion.text = "No selecciona nada el usuario"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                var informacion: String =
                    String.format("Tu nombre es %s y tu estado civil es %s", txtNombre.text.toString(),
                        parent!!.getItemAtPosition(position))
                lblInformacion.text = informacion
            }
        }
    }
}
