package com.capi.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class ScreenRecetas : AppCompatActivity() {
    var categoriaReceta = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_recetas)
        val bundle = intent.extras
        var botonPresionado = bundle?.getInt("btnPresionado")
        var recetaSeleccionada: String = ""
        val lvRecetas = findViewById<ListView>(R.id.lvRecetas)
        val btnSubirReceta = findViewById<Button>(R.id.btnCrearReceta)
        val btnMasRecetas = findViewById<Button>(R.id.btnMásRecetas)

        if(botonPresionado == 1){
            categoriaReceta = "Ensaladas"
           cargarRecetas(lvRecetas, categoriaReceta)
            this.setTitle("Ensaladas")
        }else if(botonPresionado == 2){
            categoriaReceta = "Sopas"
            cargarRecetas(lvRecetas, categoriaReceta)
            this.setTitle("Sopas y caldos")
        }else if(botonPresionado == 3){
            categoriaReceta = "Pastas"
            cargarRecetas(lvRecetas, categoriaReceta)
            this.setTitle("Pastas y pizzas")
        }else if(botonPresionado ==4){
            categoriaReceta = "Carnes y pescado"
            cargarRecetas(lvRecetas, categoriaReceta)
            this.setTitle("Carnes y pescados")
        }else if (botonPresionado == 5){
            categoriaReceta = "Postres"
            cargarRecetas(lvRecetas, categoriaReceta)
            this.setTitle("Postres")
        }else if(botonPresionado == 6){
            categoriaReceta = "Platillos fuertes"
            cargarRecetas(lvRecetas, categoriaReceta)
            this.setTitle("Platillos fuertes")
        }

        lvRecetas.setOnItemClickListener{adapterView, view, i, l ->
            recetaSeleccionada = lvRecetas.getItemAtPosition(i).toString()
            val pantallaIniciar = Intent(this, Detalle::class.java)
            pantallaIniciar.putExtra("receta", recetaSeleccionada)
            startActivityForResult(pantallaIniciar, 1)
        }

        btnSubirReceta.setOnClickListener{
            if(botonPresionado == 1){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivityForResult(pantallaIniciar, 1)
            }else if(botonPresionado ==2){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivityForResult(pantallaIniciar, 2)
            }else if(botonPresionado ==3){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivityForResult(pantallaIniciar, 3)
            }else if(botonPresionado == 4){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivityForResult(pantallaIniciar, 4)
            }else if(botonPresionado == 5){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivityForResult(pantallaIniciar, 5)
            }else if(botonPresionado ==6){
                val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
                pantallaIniciar.putExtra("btnPresionado", botonPresionado)
                startActivityForResult(pantallaIniciar, 6)
            }
        }

        btnMasRecetas.setOnClickListener{
            val pantallaNavegador = Intent(this, MasRecetas::class.java)
            pantallaNavegador.putExtra("btnReceta", botonPresionado)
            startActivity(pantallaNavegador)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val lvRecetas = findViewById<ListView>(R.id.lvRecetas)
        if(requestCode == requestCode){
            if(resultCode == resultCode){
                var mensaje = data?.getStringExtra("Mensaje")
                if(mensaje.equals("Guardado")){
                    cargarRecetas(lvRecetas, categoriaReceta)
                }
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
                    consulta.getString(3), consulta.getInt(4))
                listaRecetas.add(receta.nombreReceta)
            }
            val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaRecetas)
            listaRecetasRegistradas.adapter = adaptador
            bd.close()
        }catch (ex: NullPointerException){
            ex.printStackTrace()
        }
    }
}