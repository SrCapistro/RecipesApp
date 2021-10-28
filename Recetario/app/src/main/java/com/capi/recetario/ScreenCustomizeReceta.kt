package com.capi.recetario

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ScreenCustomizeReceta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_customize_receta)
        var listaIngredientes: MutableList<Ingrediente> = ArrayList()
        var listaCantidadIngrediente: MutableList<String> = ArrayList()
        val bundle = intent.extras
        var btnPresionado = bundle?.getInt("btnPresionado")
        var esNuevo = bundle?.getBoolean("esNuevo")
        var idReceta = bundle?.getInt("idRecetaModificar")
        val tbNombreReceta = findViewById<EditText>(R.id.tbNombreReceta)
        val tbPasosReceta = findViewById<EditText>(R.id.tbPasosReceta)
        val lvIngredientes = findViewById<ListView>(R.id.lvIngredientes)
        val btnAñadirIngrediente = findViewById<Button>(R.id.btnAñadirIngrediente)
        val tbIngrediente = findViewById<EditText>(R.id.tbIngrediente)
        val tbCantidad = findViewById<EditText>(R.id.tbCantidad)
        val tbCodigoReceta = findViewById<EditText>(R.id.tbIdReceta)
        val btnGuardarReceeta = findViewById<Button>(R.id.btnGuardarReceta)
        var tipoReceta: String = ""
        var recetaModificar = Receta()


        if(esNuevo == true){
            this.setTitle("Modificar receta")
            recetaModificar = cargarDatosRecetaModificar(idReceta)
            tbNombreReceta.setText(recetaModificar.nombreReceta)
            tbPasosReceta.setText(recetaModificar.procedimiento)
            tbCodigoReceta.setText(recetaModificar.idReceta.toString())
            tbCodigoReceta.isEnabled = false
            listaIngredientes = cargarIngredientesModificar(recetaModificar.idReceta)
            listaCantidadIngrediente = cargarListaIngredientes(listaIngredientes, lvIngredientes)
        }else{
            this.setTitle("Registrar receta nueva")
        }

        btnAñadirIngrediente.setOnClickListener{
           if(tbIngrediente.text.isBlank() || tbCantidad.text.isBlank()){
               Toast.makeText(this, "Debe de llenar los campos de ingrediente", Toast.LENGTH_SHORT).show()
           }else{
               var cantidad = tbCantidad.text.toString()
               val ingredienteNuevo = Ingrediente(tbIngrediente.text.toString(), cantidad.toInt())
               listaIngredientes.add(ingredienteNuevo)
               listaCantidadIngrediente.add("Ingrediente: "+tbIngrediente.text.toString()+" - cantidad: "+cantidad)
               val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaCantidadIngrediente)
               lvIngredientes.adapter = adaptador
               tbIngrediente.setText("")
               tbCantidad.setText("")
           }
        }

        if(btnPresionado == 1){
            tipoReceta = "Ensaladas"
        }else if(btnPresionado == 2){
            tipoReceta = "Sopas"
        }else if(btnPresionado == 3){
            tipoReceta = "Pastas"
        }else if(btnPresionado ==4){
            tipoReceta = "Carnes y pescado"
        }else if(btnPresionado == 5){
            tipoReceta = "Postres"
        }else if(btnPresionado == 6){
            tipoReceta = "Platillos fuertes"
        }

        btnGuardarReceeta.setOnClickListener{
            if(tbNombreReceta.text.isBlank() || tbCodigoReceta.text.isBlank()|| tbPasosReceta.text.isBlank()){
                Toast.makeText(this, "Debe de llenar todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                if(esNuevo == true){
                    var recetaModificar = Receta()
                    recetaModificar.nombreReceta = tbNombreReceta.text.toString()
                    recetaModificar.procedimiento = tbPasosReceta.text.toString()
                    recetaModificar.idReceta = tbCodigoReceta.text.toString().toInt()
                    modificarReceta(recetaModificar, listaIngredientes)
                }else{
                    var recetaGuardar = Receta()
                    recetaGuardar.idReceta = tbCodigoReceta.text.toString().toInt()
                    recetaGuardar.procedimiento = tbPasosReceta.text.toString()
                    recetaGuardar.categoriaReceta = tipoReceta
                    recetaGuardar.nombreReceta = tbNombreReceta.text.toString()
                    guardarRecetaNueva(recetaGuardar, listaIngredientes)
                    listaCantidadIngrediente.clear()
                }
            }
            tbNombreReceta.setText("")
            tbCodigoReceta.setText("")
            tbPasosReceta.setText("")

            Toast.makeText(this, "Se registro correctamente la receta", Toast.LENGTH_SHORT).show()
        }

    }

    fun guardarRecetaNueva(recetaGuardar: Receta, listaIngredientes: MutableList<Ingrediente>){
        try{
            val conexion = Conexion(this, "BaseRecetas", null, 1)
            val bd = conexion.writableDatabase
            val registro = ContentValues()

            registro.put("idReceta", recetaGuardar.idReceta)
            registro.put("nombreReceta", recetaGuardar.nombreReceta)
            registro.put("procedimiento", recetaGuardar.procedimiento)
            registro.put("categoria", recetaGuardar.categoriaReceta)
            bd.insert("recetas", null, registro)

            for(ingredienteNuevo: Ingrediente in listaIngredientes){
                val registroIngredientes = ContentValues()
                registroIngredientes.put("idReceta", recetaGuardar.idReceta)
                registroIngredientes.put("nombreIngrediente", ingredienteNuevo.nombreIngrediente)
                registroIngredientes.put("cantidad", ingredienteNuevo.cantidadIngrediente)
                bd.insert("ingredientes", null, registroIngredientes)
            }
            listaIngredientes.clear()
            bd.close()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    fun modificarReceta(recetaModificar: Receta, listaIngredientes: MutableList<Ingrediente>){
        try{
            val conexion = Conexion(this, "BaseRecetas", null,1)
            val bd = conexion.writableDatabase
            val registro = ContentValues()
            registro.put("nombreReceta", recetaModificar.nombreReceta)
            registro.put("procedimiento", recetaModificar.procedimiento)
            bd.update("recetas", registro,"idReceta =${recetaModificar.idReceta}", null)
            bd.delete("ingredientes","idReceta = ${recetaModificar.idReceta}",null)
            for(ingredienteNuevo: Ingrediente in listaIngredientes){
                val registroIngrediente = ContentValues()
                registroIngrediente.put("idReceta", recetaModificar.idReceta)
                registroIngrediente.put("nombreIngrediente", ingredienteNuevo.nombreIngrediente)
                registroIngrediente.put("cantidad", ingredienteNuevo.cantidadIngrediente)
                bd.insert("ingredientes", null, registroIngrediente)
            }
        }catch (ex: java.lang.Exception){
            Toast.makeText(this, "Error al modificar el elemento", Toast.LENGTH_SHORT).show()
        }
    }

    fun cargarListaIngredientes(listaIngredientes: MutableList<Ingrediente>, lvIngrediente: ListView): MutableList<String>{
        var listaNombreIngrediente: MutableList<String> = ArrayList()
        try {
            for(ingrediente: Ingrediente in listaIngredientes){
                listaNombreIngrediente.add("Ingrediente: "+ingrediente.nombreIngrediente+" | Cantidad: "+ingrediente.cantidadIngrediente)
            }
            val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaNombreIngrediente)
            lvIngrediente.adapter = adaptador
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return listaNombreIngrediente
    }

    fun cargarIngredientesModificar(idReceta: Int?): MutableList<Ingrediente>{
        var listaIngredientes: MutableList<Ingrediente> = ArrayList()
        try {
            val conexion = Conexion(this, "BaseRecetas", null, 1)
            val bd = conexion.writableDatabase
            val consulta = bd.rawQuery("select * from ingredientes where idReceta = ${idReceta}", null)
            while(consulta.moveToNext()){
                var ingrediente = Ingrediente(consulta.getString(1), consulta.getInt(2))
                listaIngredientes.add(ingrediente)
            }
        }catch (ex: java.lang.Exception){
            Toast.makeText(this, "Error de conexión con la base de datos", Toast.LENGTH_SHORT).show()
        }
        return listaIngredientes
    }

    fun cargarDatosRecetaModificar(idReceta: Int?): Receta{
        var recetaEncontrada = Receta()
        try {
            val conexion = Conexion(this, "BaseRecetas", null, 1)
            val bd = conexion.writableDatabase
            val consulta = bd.rawQuery("Select * from recetas where idReceta=${idReceta}", null)
            if(consulta.moveToNext()){
                recetaEncontrada = Receta(consulta.getInt(0), consulta.getString(1), consulta.getString(2),
                    consulta.getString(3))
            }
            bd.close()
        }catch (ex: java.lang.Exception){
            Toast.makeText(this, "Error de conexión con la base de datos", Toast.LENGTH_SHORT).show()
        }
        return recetaEncontrada
    }
}