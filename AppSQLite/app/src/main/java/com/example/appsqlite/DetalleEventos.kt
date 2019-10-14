package com.example.appsqlite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import Modelo.EventosDataSource
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.content_detalle_eventos.*

class DetalleEventos : AppCompatActivity() {


    private lateinit var datasource:EventosDataSource

    private var id = 0
    private var dia = ""
    private var descripcion = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_eventos)

        datasource = EventosDataSource(this)

        val b = this.intent.extras
        if (b != null)
        {
            id = b.getInt("id")
            dia = b.getString("dia")
            descripcion = b.getString("descripcion")

            txtdia.setText(dia)
            txtDescripcion.setText(descripcion)

        }
    }

    fun eliminar(view: View) {
        if (datasource.eliminarEvento(id))
        {
            val toast = Toast.makeText(applicationContext, "Se realizo correctamente", Toast.LENGTH_SHORT)
            toast.show()
            txtdia.setText("")
            txtDescripcion.setText("")
        }
    }


    fun GuardarEvento(view:View) {
        if (id !== 0)
        {
            //editar
            datasource.modificarEvento(txtDescripcion.text.toString(), txtdia.text.toString(), id)
            val toast = Toast.makeText(applicationContext, "Se editó correctamente", Toast.LENGTH_SHORT)
            toast.show()
        }
        else
        {
            datasource.guardarEvento(txtDescripcion.text.toString(), txtdia.text.toString())
            val toast = Toast.makeText(applicationContext, "Se guardo correctamente", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}
