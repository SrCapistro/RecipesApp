package com.capi.recetario

class Receta {
    var idReceta: Int = 0
    var nombreReceta: String = ""
    var categoriaReceta: String = ""
    var procedimiento: String = ""
    var cantidadPersonas: Int = 0

    constructor(idReceta: Int, nombreReceta: String, procedimiento: String, categoriaReceta: String, cantidadPersonas:Int){
        this.idReceta = idReceta
        this.nombreReceta = nombreReceta
        this.categoriaReceta = categoriaReceta
        this.procedimiento = procedimiento
        this.cantidadPersonas = cantidadPersonas
    }

    constructor()


}