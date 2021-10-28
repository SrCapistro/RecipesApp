package com.capi.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class ScreenRecetas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_recetas)
        val bundle = intent.extras
        var botonPresionado = bundle?.getInt("btnPresionado")
        var recetaSeleccionada: String = ""
        val lvRecetas = findViewById<ListView>(R.id.lvRecetas)
        val btnSubirReceta = findViewById<Button>(R.id.btnCrearReceta)

        if(botonPresionado == 1){
           cargarRecetas(lvRecetas, "Ensaladas")
            this.setTitle("Ensaladas")
        }else if(botonPresionado == 2){
            cargarRecetas(lvRecetas, "Sopas")
            this.setTitle("Sopas y caldos")
        }else if(botonPresionado == 3){
            cargarRecetas(lvRecetas, "Pastas")
            this.setTitle("Pastas y pizzas")
        }else if(botonPresionado ==4){
            cargarRecetas(lvRecetas, "Carnes y pescado")
            this.setTitle("Carnes y pescados")
        }else if (botonPresionado == 5){
            cargarRecetas(lvRecetas, "Postres")
            this.setTitle("Postres")
        }else if(botonPresionado == 6){
            cargarRecetas(lvRecetas, "Platillos fuertes")
            this.setTitle("Platillos fuertes")
        }

        lvRecetas.setOnItemClickListener{adapterView, view, i, l ->
            recetaSeleccionada = lvRecetas.getItemAtPosition(i).toString()
            val pantallaIniciar = Intent(this, Detalle::class.java)
            pantallaIniciar.putExtra("receta", recetaSeleccionada)
            startActivity(pantallaIniciar)
        }

        btnSubirReceta.setOnClickListener{
            if(botonPresionado == 1){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivity(pantallaIniciar)
            }else if(botonPresionado ==2){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivity(pantallaIniciar)
            }else if(botonPresionado ==3){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivity(pantallaIniciar)
            }else if(botonPresionado == 4){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivity(pantallaIniciar)
            }else if(botonPresionado == 5){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivity(pantallaIniciar)
            }else if(botonPresionado ==6){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivity(pantallaIniciar)
            }
        }
    }

    fun cargarRecetas(listaRecetasRegistradas: ListView, categoriaReceta: String){
        try {
            val conexion = Conexion(this, "BaseRecetas", null, 1)
            val bd = conexion.writableDatabase
            val consulta = bd.rawQuery("SELECT * from recetas where categoria='${categoriaReceta}'", null)
            val listaRecetas: MutableList<String> = ArrayList()
            while(consulta.moveToNext()){
                val receta = Receta(consulta.getInt(0), consulta.getString(1), consulta.getString(2),
                    consulta.getString(3))
                listaRecetas.add(receta.nombreReceta)
            }
            val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaRecetas)
            listaRecetasRegistradas.adapter = adaptador
        }catch (ex: NullPointerException){
            ex.printStackTrace()
        }
    }
}