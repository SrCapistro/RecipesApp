package com.capi.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.lang.Exception

class Detalle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        this.setTitle("Descripción de la receta")
        var receta = Receta()
        val bundle = intent.extras
        var recetaSeleccionada = bundle?.getString("receta")
        val txtNombreReceta = findViewById<TextView>(R.id.txtNombreReceta)
        val txtCategoria = findViewById<TextView>(R.id.txtCategoria)
        val lvIngredientesReceta = findViewById<ListView>(R.id.lvIngredientesReceta)
        val txtProcedimiento = findViewById<EditText>(R.id.txtProcedimiento)
        val txtCantidadPersonas = findViewById<EditText>(R.id.txtCantidadPersonas)
        val btnCalcularIngrediente = findViewById<Button>(R.id.btnCalcularIngredientes)
        val btnModificarReceta = findViewById<Button>(R.id.btnModificarReceta)
        val btnEliminarReceta = findViewById<Button>(R.id.btnEliminarReceta)

        if (recetaSeleccionada != null) {
            receta = cargarDatosReceta(recetaSeleccionada)
        }
        var listaIngredientes = cargarIngredientes(receta.idReceta)
        txtNombreReceta.setText(receta.nombreReceta)
        txtCategoria.setText("Categoria: "+receta.categoriaReceta)
        txtProcedimiento.setText(receta.procedimiento)
        cargarListaIngredientes(listaIngredientes, lvIngredientesReceta)

        btnEliminarReceta.setOnClickListener{
            eliminarReceta(receta.idReceta)
        }

        btnModificarReceta.setOnClickListener{
            var esNuevo = true
            val pantallaIniciar = Intent(this, ScreenCustomizeReceta::class.java)
            pantallaIniciar.putExtra("esNuevo", esNuevo)
            pantallaIniciar.putExtra("idRecetaModificar", receta.idReceta)
            startActivity(pantallaIniciar)
        }

        btnCalcularIngrediente.setOnClickListener{
            var cantidadPersonas = txtCantidadPersonas.text.toString().toInt()
            cargarListaCalculo(listaIngredientes, lvIngredientesReceta, cantidadPersonas)
        }
    }

    fun eliminarReceta(idReceta: Int){
        try {
            val conexion = Conexion(this, "BaseRecetas", null, 1)
            val bd = conexion.writableDatabase
            bd.delete("recetas","idReceta = ${idReceta}",null)
            this.finish()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    fun cargarListaCalculo(listaIngredientes: MutableList<Ingrediente>, lvIngrediente: ListView, cantidadPersonas: Int){
        try {
            var listaCantidadIngrediente: MutableList<String> = ArrayList()

            for(ingrediente: Ingrediente in listaIngredientes){
                listaCantidadIngrediente.add("Ingrediente: "+ingrediente.nombreIngrediente+" | Cantidad:"+ingrediente.cantidadIngrediente*cantidadPersonas)
            }
            val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaCantidadIngrediente)
            lvIngrediente.adapter = adaptador
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
    fun cargarListaIngredientes(listaIngredientes: MutableList<Ingrediente>, lvIngrediente: ListView){
       try {
           var listaNombreIngrediente: MutableList<String> = ArrayList()

           for(ingrediente: Ingrediente in listaIngredientes){
               listaNombreIngrediente.add("Ingrediente: "+ingrediente.nombreIngrediente+" | Cantidad: "+ingrediente.cantidadIngrediente)
           }
           val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaNombreIngrediente)
           lvIngrediente.adapter = adaptador
       }catch (ex: Exception){
           ex.printStackTrace()
       }
    }

    fun cargarIngredientes(idReceta: Int): MutableList<Ingrediente> {
        var listaIngredientes: MutableList<Ingrediente> = ArrayList()
        try {
            val conexion = Conexion(this, "BaseRecetas", null, 1)
            val bd = conexion.writableDatabase
            val consulta = bd.rawQuery("select * from ingredientes where idReceta = ${idReceta}", null)
            while(consulta.moveToNext()){
                var ingrediente = Ingrediente(consulta.getString(1), consulta.getInt(2))
                listaIngredientes.add(ingrediente)
            }
        }catch (ex: Exception){
            Toast.makeText(this, "Error de conexión con la base de datos", Toast.LENGTH_SHORT).show()
        }
        return listaIngredientes
    }

    fun cargarDatosReceta(nombreReceta: String): Receta {
        var recetaEncontrada = Receta()
        try {
            val conexion = Conexion(this, "BaseRecetas", null, 1)
            val bd = conexion.writableDatabase
            val consulta = bd.rawQuery("Select * from recetas where nombreReceta='${nombreReceta}'", null)
            if(consulta.moveToNext()){
                recetaEncontrada = Receta(consulta.getInt(0), consulta.getString(1), consulta.getString(2),
                consulta.getString(3))
            }
            bd.close()
        }catch (ex: Exception){
            Toast.makeText(this, "Error de conexión con la base de datos", Toast.LENGTH_SHORT).show()
        }
        return recetaEncontrada
    }
}