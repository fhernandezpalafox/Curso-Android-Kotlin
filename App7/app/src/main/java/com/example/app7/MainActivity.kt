package com.example.app7

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toast.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnToast1.setOnClickListener{view->
                    val toast1 = Toast.makeText(applicationContext, "Este es un toast", Toast.LENGTH_SHORT)
                    toast1.show()
                }


         btnToast2.setOnClickListener{view->

             val toast1 = Toast.makeText(applicationContext, "Este es un toast", Toast.LENGTH_LONG)
                 toast1.setGravity(Gravity.CENTER or Gravity.TOP, 0, 0)
                 toast1.show()

             }


          btnToast3.setOnClickListener{view->

                val toast3 = Toast(applicationContext)
                val inflater = layoutInflater

                val layout = inflater.inflate(R.layout.layout_toast, findViewById(R.id.layoutToast))
                val txtMensaje = layout.Mensaje

                txtMensaje.text = "esta es un mensaje de un toast dinamico"
                toast3.duration = Toast.LENGTH_LONG
                toast3.view = layout
                toast3.show()

            }


        btnsnackbar.setOnClickListener{view->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Snackbar.make(view, "Este es un mensaje de prueba", Snackbar.LENGTH_SHORT)
                    .setActionTextColor(resources.getColor( R.color.snackbar_action,null))
                    .setAction("Aceptar") {
                        val toast1 = Toast.makeText(getApplicationContext(),
                            "Este es un toast", Toast.LENGTH_SHORT)
                        toast1.show()
                    }
                    .show()
            }
        }

    }
}
