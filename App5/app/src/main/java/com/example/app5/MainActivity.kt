package com.example.app5

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Eventos()
    }


    fun Eventos() {

        btnAlerta1.setOnClickListener { view ->
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Mensaje de prueba")
                .setTitle("Mensaje")
                .setPositiveButton("Aceptar")
                { dialog, which ->
                    lblinformacion.text = "Respuesta de nuestra alerta"
                }
            builder.create().show()
        }



        btnAlerta2.setOnClickListener { view ->
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Mensaje de prueba")
                    .setTitle("Mensaje")
                    .setPositiveButton("Aceptar") { dialog, which ->
                        lblinformacion.setText("Respuesta positiva")
                    }
                    .setNegativeButton("Cancelar")
                     { dialog, which ->

                            lblinformacion.text = "respuesta Negativa"
                            dialog.cancel()

                    }
                builder.create().show()

        }


        btnAlerta3.setOnClickListener { view ->

                val datos = arrayOf("Felipe", "Oscar", "Juan")

                val builder = AlertDialog.Builder(this@MainActivity)

                builder.setTitle("Seleciona un nombre")
                    .setItems(datos)
                    {  dialog:DialogInterface, item:Int ->
                        lblinformacion.text = String.format("Tu nombre es %s", datos[item])
                    }
                builder.create().show()


                //Alerta #3
                val builder2 = AlertDialog.Builder(this@MainActivity)
                builder2.setTitle("Selecciona")
                    .setSingleChoiceItems(datos, -1)
                    { dialog:DialogInterface, item:Int ->
                        lblinformacion.text = String.format("Tu nombre es %s", datos[item])
                    }
                 builder2.create().show()


                //Alerta #4
                val builder3 = AlertDialog.Builder(this@MainActivity)
                builder3.setTitle("Selecciona")
                    .setMultiChoiceItems(datos, null)
                       {dialog:DialogInterface, items:Int, isChecked:Boolean ->
                            if (isChecked)
                            {
                                val informacion = lblinformacion.text.toString()
                                lblinformacion.text = informacion + String.format("%s", datos[items]) + ","
                            }
                        }
                builder3.create().show()
            }
        }
    }

