package com.capi.recetario

class Ingrediente {
    var nombreIngrediente: String
    var cantidadIngrediente: Double
    var tipoIngrediente: String

    constructor(nombreIngrediente: String, cantidadIngrediente: Double, tipoIngrediente: String){
        this.nombreIngrediente = nombreIngrediente
        this.cantidadIngrediente = cantidadIngrediente
        this.tipoIngrediente = tipoIngrediente
    }


}