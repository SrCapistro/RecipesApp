package com.capi.recetario

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import jp.wasabeef.blurry.Blurry

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTitle("Mi Recetario")
        var btnPresionado = 0;
        setContentView(R.layout.activity_main)
        val btnEnsaladas = findViewById<ImageButton>(R.id.btnEnsaladas)
        val btnPostres = findViewById<ImageButton>(R.id.btnPostres)
        val btnCarnes = findViewById<ImageButton>(R.id.btnCarnes)
        val btnPasta = findViewById<ImageButton>(R.id.btnPastas)
        val btnPlatoFuerte = findViewById<ImageButton>(R.id.btnPlatosFuertes)
        val btnSopas = findViewById<ImageButton>(R.id.btnSopas)
        btnEnsaladas.setOnClickListener{
            btnPresionado = 1
            val pantallaIniciar = Intent(this, ScreenRecetas::class.java)
            pantallaIniciar.putExtra("btnPresionado", btnPresionado)
            startActivity(pantallaIniciar)
        }

        btnSopas.setOnClickListener{
            btnPresionado = 2
            val pantallaIniciar = Intent(this, ScreenRecetas::class.java)
            pantallaIniciar.putExtra("btnPresionado", btnPresionado)
            startActivity(pantallaIniciar)
        }

        btnPasta.setOnClickListener{
            btnPresionado = 3
            val pantallaIniciar = Intent(this, ScreenRecetas::class.java)
            pantallaIniciar.putExtra("btnPresionado", btnPresionado)
            startActivity(pantallaIniciar)
        }

        btnCarnes.setOnClickListener {
            btnPresionado = 4
            val pantallaIniciar = Intent(this, ScreenRecetas::class.java)
            pantallaIniciar.putExtra("btnPresionado", btnPresionado)
            startActivity(pantallaIniciar)
        }

        btnPostres.setOnClickListener{
            btnPresionado = 5
            val pantallaIniciar = Intent(this, ScreenRecetas::class.java)
            pantallaIniciar.putExtra("btnPresionado", btnPresionado)
            startActivity(pantallaIniciar)
        }

        btnPlatoFuerte.setOnClickListener{
            btnPresionado = 6
            val pantallaIniciar = Intent(this, ScreenRecetas::class.java)
            pantallaIniciar.putExtra("btnPresionado", btnPresionado)
            startActivity(pantallaIniciar)
        }

    }
}