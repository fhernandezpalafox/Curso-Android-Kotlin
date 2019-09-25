package com.example.appnavview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_layout.*
import layout.Fragmento1
import layout.Fragmento2
import layout.Fragmento3

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(appbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_nav_menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        navview.setNavigationItemSelectedListener { menuItem->
            var fragmentTransaction = false
            var fragment: Fragment? = null

            when (menuItem.getItemId()) {
                R.id.menu_seccion_1 -> {
                    fragment = Fragmento1()
                    fragmentTransaction = true
                }
                R.id.menu_seccion_2 -> {
                    fragment = Fragmento2()
                    fragmentTransaction = true
                }
                R.id.menu_seccion_3 -> {
                    fragment = Fragmento3()
                    fragmentTransaction = true
                }
                R.id.menu_opcion_1 -> Log.i("NavigationView", "Pulsada opción 1")
                R.id.menu_opcion_2 -> Log.i("NavigationView", "Pulsada opción 2")
            }
            if (fragmentTransaction) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment!!)
                    .commit()
                menuItem.setChecked(true)
                supportActionBar!!.setTitle(menuItem.getTitle())
            }
            drawer_layout.closeDrawers()
            true
        }
    }


    override fun  onCreateOptionsMenu(menu:Menu):Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }



    override fun onOptionsItemSelected(item:MenuItem):Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
