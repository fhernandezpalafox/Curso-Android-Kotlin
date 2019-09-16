package com.example.appcoordinatorlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView

class Main4Activity : AppCompatActivity() {

    var imagen:Int = 0
    var nombre:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)



        //funcionamiento Toolbar
        setToolbar() //añadir action bar
        if (supportActionBar != null)
        { //habilitar up button
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        //Hacer traslucido la parte superior
        setStatusBarTranslucent(true)
        if (getIntent().getExtras() != null)
        {
            nombre = intent.extras.getString("nombre")
            imagen = intent.extras.getInt("imagen")
        }
        val ctlLayout = findViewById(R.id.ctlLayout) as CollapsingToolbarLayout
        ctlLayout.title = nombre

        val imgToolbar = findViewById(R.id.imgToolbar) as ImageView
        imgToolbar.setImageResource(imagen)

        //BARRA DE NAVEGACION TRASLUCIDA
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

    }

    private fun setStatusBarTranslucent(makeTranslucent:Boolean) {
        if (makeTranslucent)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        else
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    private fun setToolbar() {
        val toolbar = findViewById(R.id.appbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun showSnackBar(msg:String) {
        Snackbar
            .make(findViewById(R.id.coordinatorLayout), msg, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.btnAtras -> {
                showSnackBar("Se presiono el boton de acerca")
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}

