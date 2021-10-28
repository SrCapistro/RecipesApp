package com.capi.recetario

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Conexion (context: Context, name:String, factory:SQLiteDatabase.CursorFactory?, version:Int ): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table recetas (idReceta int primary key, nombreReceta text, procedimiento text, categoria text)")
        db?.execSQL("create table ingredientes (idReceta int, nombreIngrediente text, cantidad int, FOREIGN KEY(idReceta) REFERENCES recetas(idReceta))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}