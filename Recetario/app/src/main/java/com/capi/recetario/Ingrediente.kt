package com.capi.recetario

class Ingrediente {
    var nombreIngrediente: String
    var cantidadIngrediente: Int
    var tipoIngrediente: String

    constructor(nombreIngrediente: String, cantidadIngrediente: Int, tipoIngrediente: String){
        this.nombreIngrediente = nombreIngrediente
        this.cantidadIngrediente = cantidadIngrediente
        this.tipoIngrediente = tipoIngrediente
    }


}