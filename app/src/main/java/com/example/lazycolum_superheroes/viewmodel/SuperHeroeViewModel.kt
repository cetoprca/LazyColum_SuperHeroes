package com.example.lazycolum_superheroes.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lazycolum_superheroes.data.SuperHeroeProveedor
import com.example.lazycolum_superheroes.model.SuperHeroe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SuperHeroeViewModel: ViewModel(){
    private var _superHeroes = MutableStateFlow<List<SuperHeroe>>(listOf())
    val superheroes: StateFlow<List<SuperHeroe>> = _superHeroes

    private var _selectedSuperHeroes = MutableStateFlow<List<SuperHeroe>>(listOf())
    val selectedSuperHeroes: StateFlow<List<SuperHeroe>> = _selectedSuperHeroes

    private var _action_mode = MutableStateFlow(false)
    val action_mode: StateFlow<Boolean> = _action_mode
    private var _mostrar_dialogo = MutableStateFlow(false)
    val mostrar_dialogo: StateFlow<Boolean> = _mostrar_dialogo

    fun setActionMode(action_mode: Boolean){
        _action_mode.value = action_mode
    }

    fun setMostrarDialogo(action_mode: Boolean){
        _mostrar_dialogo.value = action_mode
    }

    //Inicializo superHeroes
    init {
        _superHeroes.value = SuperHeroeProveedor.SuperHeroeList
    }

    fun seleccionarHeroe(heroe: SuperHeroe){
        _selectedSuperHeroes.value = ArrayList(_selectedSuperHeroes.value + heroe)
    }

    fun deseleccionarHeroe(heroe: SuperHeroe){
        _selectedSuperHeroes.value = ArrayList(_selectedSuperHeroes.value - heroe)
    }

    fun isHeroeSeleccionado(heroe: SuperHeroe) : Boolean{
        return _selectedSuperHeroes.value.contains(heroe)
    }

    fun isSeleccionEmpty() : Boolean{
        return _selectedSuperHeroes.value.isEmpty()
    }

    fun clearSeleccion(){
        _selectedSuperHeroes.value = emptyList()
    }

    fun borrarHeroe(heroe: SuperHeroe) {
        _superHeroes.value = _superHeroes.value - heroe
    }

    fun actualizarHeroe(heroeoriginal: SuperHeroe,heroeactualizado: SuperHeroe)
    {
        //voy a buscar el heroeoriginal
        val indice=_superHeroes.value.indexOf(heroeoriginal)
        if(indice!=-1)
        {
            //Encontrado, actualizo la lista
            _superHeroes.value = _superHeroes.value.mapIndexed { index, heroe -> if (heroe == heroeoriginal) heroeactualizado else heroe}
        }
    }
}