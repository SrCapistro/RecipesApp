package com.capi.recetario

class Receta {
    var idReceta: Int = 0
    var nombreReceta: String = ""
    var categoriaReceta: String = ""
    var procedimiento: String = ""

    constructor(idReceta: Int, nombreReceta: String, procedimiento: String, categoriaReceta: String){
        this.idReceta = idReceta
        this.nombreReceta = nombreReceta
        this.categoriaReceta = categoriaReceta
        this.procedimiento = procedimiento
    }

    constructor()


}