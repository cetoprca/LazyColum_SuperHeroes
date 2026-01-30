package com.example.lazycolum_superheroes.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lazycolum_superheroes.data.SuperHeroeProveedor
import com.example.lazycolum_superheroes.model.SuperHeroe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SuperHeroeViewModel: ViewModel(){

    // Creamos la lista state de los superheroes con su getter
    private var _superHeroes = MutableStateFlow<List<SuperHeroe>>(emptyList())
        val superheroes: StateFlow<List<SuperHeroe>> = _superHeroes.asStateFlow()



    // Creamos la lista state de los superheroes seleccionados con su getter
    private var _selectedSuperHeroes = MutableStateFlow<List<SuperHeroe>>(emptyList())
        val selectedSuperHeroes: StateFlow<List<SuperHeroe>> = _selectedSuperHeroes.asStateFlow()



    // Creamos la lista state de los superheroes favoritos con su getter
    private var _favoriteSuperHeroes = MutableStateFlow<List<SuperHeroe>>(emptyList())
    val favoriteSuperHeroes: StateFlow<List<SuperHeroe>> = _favoriteSuperHeroes.asStateFlow()



    // Creamos el booleano para activar el modo de seleccion y su getter
    private var _actionMode = MutableStateFlow(false)
        val actionMode: StateFlow<Boolean> = _actionMode.asStateFlow()



    // Creamos el booleano para activar el dialogo de edicion y su getter
    private var _mostrarDialogo = MutableStateFlow(false)
        val mostrarDialogo: StateFlow<Boolean> = _mostrarDialogo.asStateFlow()



    //Inicializo superHeroes
    init {
        _superHeroes.value = SuperHeroeProveedor.SuperHeroeList
    }


    // Metodos set para los booleanos
    fun setActionMode(actionMode: Boolean){
        _actionMode.value = actionMode
    }

    fun setMostrarDialogo(mostrarDialogo: Boolean){
        _mostrarDialogo.value = mostrarDialogo
    }


    // Añadir o eliminar heroe de la lista de favoritos
    fun toggleFavorito(heroe: SuperHeroe){
        _favoriteSuperHeroes.update { actuales ->
            if (actuales.contains(heroe)){
                actuales - heroe
            }else{
                actuales + heroe
            }
        }
    }


    // Metodo para seleccionar o deseleccionar heroe
    fun seleccionarHeroe(heroe: SuperHeroe){
        _selectedSuperHeroes.update { actuales ->
            if (actuales.contains(heroe)){
                actuales - heroe
            }else{
                actuales + heroe
            }
        }

        setActionMode(_selectedSuperHeroes.value.isNotEmpty()) //Activar o desactivar el modo de seleccion
    }


    // Limpiar lista de seleccionados
    fun clearSeleccion(){
        _selectedSuperHeroes.value = emptyList()
    }


    // Borrar un heroe en especifico
    fun borrarHeroe(heroe: SuperHeroe) {
        _superHeroes.value -= heroe
        _favoriteSuperHeroes.value -= heroe
    }


    // Actualizar un heroe
    fun actualizarHeroe(heroeoriginal: SuperHeroe,heroeactualizado: SuperHeroe)
    {
        //actualizamos el heroe dentro de la lista de heroes
        var indice=_superHeroes.value.indexOf(heroeoriginal)
        if(indice!=-1)
        {
            //Encontrado, actualizo la lista
            _superHeroes.value = _superHeroes.value.mapIndexed { _, heroe -> if (heroe == heroeoriginal) heroeactualizado else heroe}
        }

        //actualizamos el heroe dentro de la lista de heroes favoritos
        indice=_favoriteSuperHeroes.value.indexOf(heroeoriginal)
        if(indice!=-1)
        {
            //Encontrado, actualizo la lista
            _favoriteSuperHeroes.value = _favoriteSuperHeroes.value.mapIndexed { _, heroe -> if (heroe == heroeoriginal) heroeactualizado else heroe}
        }
    }
}