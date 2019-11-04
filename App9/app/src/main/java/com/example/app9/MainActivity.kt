package com.example.app9

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnMapa1.setOnClickListener {
                val intent = Intent(this@MainActivity, MapsActivity::class.java)
                startActivity(intent)
        }

    }
}
