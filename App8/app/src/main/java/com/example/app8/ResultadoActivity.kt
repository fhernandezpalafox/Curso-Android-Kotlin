package com.example.app8

import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_resultado.*

class ResultadoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val bundle = intent.extras
        if (bundle.getString("parametro") != null)
        {
            lblparametro.text = bundle.getString("parametro")
            val ns = Context.NOTIFICATION_SERVICE
            val notificationManager = baseContext.getSystemService(ns) as NotificationManager
            notificationManager.cancel(bundle.getInt("idNotificacion"))
        }
    }
}
