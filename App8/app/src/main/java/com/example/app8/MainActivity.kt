package com.example.app8

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var NumeroNotificaciones = 5


    private var mManager: NotificationManager? = null

    fun getManager() : NotificationManager {
            if (mManager == null)
            {
                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager as NotificationManager
    }



    fun shortCustDinamico() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return
        val idNoticacion = 234
        val shortcutManager = applicationContext.getSystemService(ShortcutManager::class.java)
        val nuevaTareaIntent = Intent(applicationContext, ResultadoActivity::class.java)
        nuevaTareaIntent.action = Intent.ACTION_VIEW
        nuevaTareaIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        nuevaTareaIntent.putExtra("parametro", "valor 1")
        nuevaTareaIntent.putExtra("idNotificacion", idNoticacion)
        val resultIntent = Intent(this@MainActivity, ResultadoActivity::class.java)
        resultIntent.putExtra("parametro", "valor 1")
        resultIntent.putExtra("idNotificacion", idNoticacion)
        val postShortcut = ShortcutInfo.Builder(applicationContext, "1")
            .setShortLabel("Informacion")
            .setLongLabel("Informacion importante...")
            .setIcon(Icon.createWithResource(applicationContext, R.mipmap.ic_launcher))
            .setIntent(nuevaTareaIntent)
            .build()

        val shortcutInfoList = mutableListOf<ShortcutInfo>()
            shortcutInfoList.add(postShortcut)

        shortcutManager.addDynamicShortcuts(shortcutInfoList)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val canal1Prueba = NotificationChannel("com.prueba.canalPrueba1",
                "Canal de prueba 1", NotificationManager.IMPORTANCE_HIGH)
            canal1Prueba.enableLights(true)
            canal1Prueba.enableVibration(true)
            canal1Prueba.lightColor = Color.GREEN
            canal1Prueba.lockscreenVisibility = Notification.BADGE_ICON_SMALL
            getManager().createNotificationChannel(canal1Prueba)
            val canal2Prueba = NotificationChannel("com.prueba.canalPrueba2",
                "Canal de prueba 2", NotificationManager.IMPORTANCE_HIGH)
            canal2Prueba.enableLights(true)
            canal2Prueba.enableVibration(true)
            canal2Prueba.lightColor = Color.GREEN
            canal2Prueba.lockscreenVisibility = Notification.BADGE_ICON_SMALL
            getManager().createNotificationChannel(canal2Prueba)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shortCustDinamico()

        createNotificationChannel()

        btnotificacion1.setOnClickListener { view ->
                val builder = NotificationCompat.Builder(applicationContext, "com.prueba.canalPrueba1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                    .setContentTitle("Ejemplo de notificacion")
                    .setContentText("Ese es mi contenido de notificacion")
                    .setTicker("Ejemplo de notificacion")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                getManager().notify(0, builder.build())
        }

        btnotificacion2.setOnClickListener { view ->

            val idNoticacion = 234
            val resultIntent = Intent(this@MainActivity, ResultadoActivity::class.java)
            resultIntent.putExtra("parametro", "valor 1")
            resultIntent.putExtra("idNotificacion", idNoticacion)
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(this@MainActivity,
                1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val inboxStyle = NotificationCompat.InboxStyle()
            inboxStyle.setBigContentTitle("Notificacion personalizada")
            inboxStyle.addLine("Esta es una linea numero 1")
            inboxStyle.addLine("Esta es una linea numero 2")
            inboxStyle.addLine("Esta es una linea numero 3")
            inboxStyle.addLine("Esta es una linea numero 4")
            inboxStyle.addLine("Esta es una linea numero 5")
            inboxStyle.addLine("Esta es una linea numero 6")
            NumeroNotificaciones += 1
            inboxStyle.setSummaryText(String.format("+ %d mas", NumeroNotificaciones))
            val Nbuilder:NotificationCompat.Builder =
                NotificationCompat.Builder(this@MainActivity, "com.prueba.canalPrueba2")
            Nbuilder.setSmallIcon(R.mipmap.ic_launcher)
            Nbuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
            Nbuilder.setContentTitle("Ejemplo de notificacion")
            Nbuilder.setContentText("Esre es mi contenido de notificacion")
            Nbuilder.setTicker("Ejemplo de notificacion")
            Nbuilder.setVibrate(longArrayOf(100, 250, 100, 500))
            Nbuilder.setStyle(inboxStyle)
            Nbuilder.addAction(R.drawable.ic_chat_black_24dp, "Ver Mensaje", pendingIntent)
            getManager().notify(0, Nbuilder.build())


        }


        btnotificacion3.setOnClickListener {

            val textoLargo = NotificationCompat.BigTextStyle()
            textoLargo.bigText("Android Studio es el entorno de desarrollo integrado (IDE) oficial para el desarrollo de aplicaciones para Android y se basa en IntelliJ IDEA . Además del potente editor de códigos y las herramientas para desarrolladores de IntelliJ, Android Studio ofrece aún más funciones que aumentan tu productividad durante la compilación de apps para Android")
            textoLargo.setBigContentTitle("Android Studio")
            textoLargo.setSummaryText("Hecho por: Android")
            val Nbuilder:NotificationCompat.Builder
            Nbuilder = NotificationCompat.Builder(this@MainActivity, "com.prueba.canalPrueba1")
            Nbuilder.setSmallIcon(R.mipmap.ic_launcher)
            Nbuilder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            Nbuilder.setContentTitle("Ejemplo de notificacion")
            Nbuilder.setContentText("Esre es mi contenido de notificacion")
            Nbuilder.setTicker("Ejemplo de notificacion")
            Nbuilder.setVibrate(longArrayOf(100, 250, 100, 500))
            Nbuilder.setStyle(textoLargo)
            getManager().notify(0, Nbuilder.build())
        }

        btnotificacion4.setOnClickListener {

            val idNoticacion = 234
            val resultIntent = Intent(this@MainActivity, ResultadoActivity::class.java)
            resultIntent.putExtra("parametro", "valor 1")
            resultIntent.putExtra("idNotificacion", idNoticacion)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this@MainActivity,
                1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val bigPictureStyle = NotificationCompat.BigPictureStyle()
            bigPictureStyle.bigPicture(BitmapFactory.decodeResource(resources, R.drawable.androidlogo2)).build()
            val Nbuilder:NotificationCompat.Builder
            Nbuilder = NotificationCompat.Builder(this@MainActivity, "com.prueba.canalPrueba2")
            Nbuilder.setSmallIcon(R.mipmap.ic_launcher)
            Nbuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
            Nbuilder.setContentTitle("Ejemplo de notificacion")
            Nbuilder.setContentText("Esre es mi contenido de notificacion")
            Nbuilder.setTicker("Ejemplo de notificacion")
            Nbuilder.setVibrate(longArrayOf(100, 250, 100, 500))
            Nbuilder.setStyle(bigPictureStyle)
            Nbuilder.addAction(R.drawable.ic_share_black_24dp, "Compartir", pendingIntent)
            Nbuilder.addAction(R.drawable.ic_send_black_24dp, "Enviar", pendingIntent)
            getManager().notify(0, Nbuilder.build())

        }
    }
}
