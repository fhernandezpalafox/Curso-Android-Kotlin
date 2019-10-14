package Modelo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME = "Eventos.db"
val DATABASE_VERSION = 1

class BdOpenHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(EventosDataSource.CREATE_EVENTOS_SCRIPT)
        db.execSQL(EventosDataSource.INSERT_EVENTOS_SCRIPT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}