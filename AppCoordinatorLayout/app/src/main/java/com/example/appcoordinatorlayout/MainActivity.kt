package com.example.appcoordinatorlayout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Operaciones()
    }

    fun Operaciones(){

        btnIr.setOnClickListener{ view->
            val i = Intent(applicationContext, Main2Activity::class.java)
            startActivity(i)
        }

        btnlistaConImagen.setOnClickListener{view->
            val i = Intent(applicationContext, Main3Activity::class.java)
            startActivity(i)
        }
    }
}
