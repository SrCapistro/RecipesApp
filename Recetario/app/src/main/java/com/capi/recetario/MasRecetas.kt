package com.capi.recetario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class MasRecetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mas_recetas)

        val bundle = intent.extras
        val categoReceta = bundle?.getInt("btnReceta")
        var urlReceta: String = ""
        val webViewRecetas = findViewById<WebView>(R.id.wbRecetas)
        if(categoReceta == 1){
            urlReceta = "https://www.myfoodandfamily.com/es-US/search?searchTerm=ensaladas"
        }else if(categoReceta == 2){
            urlReceta = "https://www.myfoodandfamily.com/es-US/search?searchTerm=sopas"
        }else if (categoReceta == 3){
            urlReceta = "https://www.myfoodandfamily.com/es-US/search?searchTerm=pastas"
        }else if(categoReceta == 4){
            urlReceta = "https://www.myfoodandfamily.com/es-US/search?searchTerm=pescado"
        }else if(categoReceta == 5){
            urlReceta = "https://www.myfoodandfamily.com/es-US/recipes/1000165/postres"
        }else if(categoReceta == 6){
            urlReceta = "https://www.myfoodandfamily.com/es-US/recipes/1000026/recetas-de-cena"
        }

        webViewRecetas.loadUrl(urlReceta)
    }
}