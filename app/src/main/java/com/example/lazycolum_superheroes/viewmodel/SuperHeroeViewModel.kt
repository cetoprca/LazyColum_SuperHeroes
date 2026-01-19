package com.example.lazycolum_superheroes.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lazycolum_superheroes.data.SuperHeroeProveedor
import com.example.lazycolum_superheroes.model.SuperHeroe


class SuperHeroeViewModel: ViewModel(){
    var superHeroes = mutableStateListOf<SuperHeroe>()
    private set //para que no pueda modificar desde fuera

    //Inicializo superHeroes
    init {
        superHeroes.addAll(SuperHeroeProveedor.SuperHeroeList)
    }

    fun borrarHeroe(heroe: SuperHeroe) {
        superHeroes.remove(heroe)
    }

    fun actualizarHeroe(heroeoriginal: SuperHeroe,heroeactualizado: SuperHeroe)
    {
        //voy a buscar el heroeoriginal
        val indice=superHeroes.indexOf(heroeoriginal)
        if(indice!=-1)
        {
            //Encontrado, actualizo la lista
            superHeroes[indice]=heroeactualizado
        }
    }
}