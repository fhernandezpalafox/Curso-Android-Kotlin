package com.example.app12

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var imgLlamar:ImageView? = null
    private var imgMensaje:ImageView? = null
    private val REQUEST_PHONE_CALL = 1

    val isPermissionGranted:Boolean
        get() {
            if (Build.VERSION.SDK_INT >= 23)
            {
                if ((checkSelfPermission(android.Manifest.permission.CALL_PHONE) === PackageManager.PERMISSION_GRANTED))
                {
                    Log.v("TAG", "Permission is granted")
                    return true
                }
                else
                {
                    Log.v("TAG", "Permission is revoked")
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
                    return false
                }
            }
            return false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        imgLlamar = findViewById(R.id.imgLlamar)
        imgMensaje = findViewById(R.id.imgMensaje)
        imgLlamar!!.setOnClickListener {
            if (isPermissionGranted) {
                call_action()
            }
        }
        imgMensaje!!.setOnClickListener {
            val compartirMensaje = Intent(Intent.ACTION_SEND)
            compartirMensaje.setType("text/plain")
            compartirMensaje.putExtra(Intent.EXTRA_TEXT, "Este es un mensaje de prueba")
            // compartirMensaje.setPackage("com.whatsapp");
            startActivity(compartirMensaje)
        }
    }

    fun call_action() {
        val phnum = "47724099786"
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phnum")
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_PHONE_CALL)
            return
        }
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode:Int,
                                            permissions:Array<String>, grantResults:IntArray) {
        when (requestCode) {
            REQUEST_PHONE_CALL -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT).show()
                    call_action()
                }
                else
                {
                    Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }


}
