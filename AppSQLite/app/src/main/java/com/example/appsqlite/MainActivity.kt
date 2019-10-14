package com.example.appsqlite

import Modelo.EventosDataSource
import Entidades.Evento
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lista_eventos.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LlenarInformacion()
    }



    fun LlenarInformacion() {
        val datasource = EventosDataSource(this)

        //INSERTAR DATOS
        //datasource.guardarEvento("Clausura del festival","Martes 17 Nov.");

        val registros = ArrayList<Evento>()
        // SE ESTA LLAMANDO AL METODO PARA TRAERNOS TODA LA INFORMACION DE LA BD
        val cursor = datasource.consultarEventos()

        while (cursor.moveToNext())
        {
            val columnas = Evento(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            )
            registros.add(columnas)
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,registros);
        val adaptador = AdpatadorEventos(this, registros)

        listaeventos.adapter = adaptador

        listaeventos.setOnItemClickListener { parent, view, position, id ->

                val item = parent.getItemAtPosition(position) as Evento

                val intent = Intent(this@MainActivity, DetalleEventos::class.java)

                intent.putExtra("id", item.iD_EVENTO)
                intent.putExtra("dia", item.diA_EVENTO)
                intent.putExtra("descripcion", item.descripcioN_EVENTO)

                startActivity(intent)

        }
    }

    fun AgregarEventos(view:View) {
        val intent = Intent(this@MainActivity, DetalleEventos::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        this.LlenarInformacion()
    }

    override fun onResume() {
        super.onResume()
        this.LlenarInformacion()
    }


    internal class AdpatadorEventos(context: Context, datos:List<Evento>):
        ArrayAdapter<Evento>(context, R.layout.lista_eventos, datos) {

        var _datos:List<Evento>

        init{
            _datos = datos
        }

        @NonNull
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?):View {

            val inflater = convertView ?: LayoutInflater.from(context).inflate(R.layout.lista_eventos, parent, false)

            val currentEntity = getItem(position)


            inflater.LblTitulo.text  = currentEntity.descripcioN_EVENTO


            return inflater


        }

    }

}
