package com.example.appcoordinatorlayout


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.layout_persona.view.*

class Main3Activity : AppCompatActivity() {

     lateinit var listaPersonas: ArrayList<Persona>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        Inicializar()
        Eventos()

    }

    fun Inicializar(){

        listaPersonas = ArrayList()
        listaPersonas.add(Persona("Bill gates", "Microsoft", R.drawable.c_bill_gates, R.drawable.g_bill_gates))
        listaPersonas.add(Persona("Larry page", "Google", R.drawable.c_larry_page, R.drawable.g_larry_page))
        listaPersonas.add(Persona("Sergey brin", "Google", R.drawable.c_sergey_brin, R.drawable.g_sergey_brin))

        val adaptadorPersonas = AdaptadorPersonas(this)

        Lista.adapter = adaptadorPersonas
    }


    fun Eventos(){

        Lista.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(applicationContext, Main4Activity::class.java)
            i.putExtra("nombre", listaPersonas[position].nombre)
            i.putExtra("imagen", listaPersonas[position].imagenGrande)
            startActivity(i)
        }
    }


    internal inner class AdaptadorPersonas(context:AppCompatActivity):
                   ArrayAdapter<Persona>(context, 0, listaPersonas) {


        @NonNull
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?):View {

            val inflater = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_persona, parent, false)

            val currentPersona = getItem(position)


            inflater.txtNombrePersona.text  = currentPersona.nombre
            inflater.txtEmpresaPersona.text = currentPersona.empresa
            inflater.ImgPersona2.setImageResource(currentPersona.imagen)

            return inflater


        }


    }
}
