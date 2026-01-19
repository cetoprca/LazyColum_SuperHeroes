package com.example.lazycolum_superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazycolum_superheroes.model.SuperHeroe
import com.example.lazycolum_superheroes.ui.components.DialogoSuperHeroe
import com.example.lazycolum_superheroes.ui.theme.LazyColumn_SuperHeroes_composeTheme
import com.example.lazycolum_superheroes.viewmodel.SuperHeroeViewModel

import com.example.recycler_superheroes_compose.ui.components.BottomNavigationBar_SuperHeroe
import com.example.recycler_superheroes_compose.ui.components.ElementoLazySuperHeroe
import com.example.recycler_superheroes_compose.ui.components.TopAppBar_ActionMode
import com.example.recycler_superheroes_compose.ui.components.TopAppBar_Normal



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

           LazyColumn_SuperHeroes_composeTheme {

              Pantalla()
            }
        }
    }
}


@Composable
fun Pantalla()
{
    val superheroeViewModel: SuperHeroeViewModel = viewModel()
    //Variable para controlar si se muestra el Dialogo de editar SuperHeroe
    var mostrar_dialogo_editar by remember { mutableStateOf(false) }
    //Definimos una variable para saber si esta activado el menu de acción contextual en el TopBar
    var action_mode by remember { mutableStateOf(false) }
    //Defino un listado de elementos seleccionados, solamente su posición
    val lista_seleccionados=remember { mutableStateListOf<Int>() }


    //statusBarPadding() deja la barra de estado libre para que
    //no se aplique el color del TopBar
    Scaffold(modifier = Modifier.fillMaxSize().statusBarsPadding(), topBar ={
        //El ToolBar que se muestra depende si esta activado el action_mode
        if(!action_mode) {
            TopAppBar_Normal(Modifier)
        }
        else
        {
            TopAppBar_ActionMode(modificador = Modifier,
                lista_seleccionados.size,
                click_atras = {
                    //vacio la lista de seleccionados
                    lista_seleccionados.clear()
                    action_mode=false

                },
                click_editar = {
                    //ABrir un Dialog para editar el elemento seleccionado
                    //Se supone que solo tiene que haber un elemento seleccionado
                    if(lista_seleccionados.size==1)
                    {
                      mostrar_dialogo_editar=true
                    }

                },
                click_eliminar = {
                    //Tengo que eliminar todos los elementos seleccionados
                    lista_seleccionados.forEach {
                        superheroeViewModel.borrarHeroe(superheroeViewModel.superHeroes.get(it))
                    }
                    //Elimino la lista de seleccionados
                    lista_seleccionados.clear()
                    //Cierro el action_mode
                    action_mode=false

                })
        }
        },
        bottomBar = {BottomNavigationBar_SuperHeroe()


        }) { innerPadding ->
        ZonaCentral(Modifier.padding(innerPadding),superheroeViewModel.superHeroes,
            borrar_superheroe = {
            superheroeViewModel.borrarHeroe(it)
        },
            click_corto_elemento = {
                if(action_mode)
                {
                    //Añado el elemento a la lista de seleccionados,si no esta seleccionado previamente
                    if(lista_seleccionados.contains(it))
                    {
                        //El elemento ya se ha añadido a la lista lo que hago es eliminarlo
                        lista_seleccionados.remove(it)
                        //Si ademas ya no hay elementos seleccionados cierro el action_mode
                        if(lista_seleccionados.size==0)
                            action_mode=false
                    }
                    else {
                        //Si no lo añado
                        lista_seleccionados.add(it)
                    }
                }

            },
            click_largo_elemento = {indice_elemento->
                if(!action_mode)
                {
                    action_mode=true
                    lista_seleccionados.add(indice_elemento)
                }
            },
            esta_seleccionado = {indice->
                lista_seleccionados.contains(indice)
            })
        if(mostrar_dialogo_editar)
        {
            DialogoSuperHeroe(superheroeViewModel.superHeroes.get(lista_seleccionados.get(0)),
                onDismiss = {mostrar_dialogo_editar=false},
                onGuardar = { superheroe->
                    //Guardo los datos del superheroe
                    superheroeViewModel.superHeroes.get(lista_seleccionados.get(0)).copy(superheroe.nombre, publicador = superheroe.publicador)
                    //Cierro el dialogo
                    mostrar_dialogo_editar=false
                    //Vacio la lista de seleccinados
                    lista_seleccionados.clear()
                    //Salgo del action_mode
                    action_mode=false
                })
        }
    }
}


@Composable
fun ZonaCentral(modificador: Modifier= Modifier, heroes:List<SuperHeroe>, borrar_superheroe:(SuperHeroe)->Unit,click_corto_elemento:(Int)->Unit,click_largo_elemento:(Int)->Unit,esta_seleccionado:(Int)->Boolean)
{

    LazyColumn(modifier = modificador.fillMaxSize()) {
        //Mostramos los elementos
        itemsIndexed(heroes){indice,her->
            ElementoLazySuperHeroe(her,
                Modifier,
                selecionado = {esta_seleccionado(indice)},
                click_borrar = {borrar_superheroe(her)},
                click_corto = {click_corto_elemento(indice)},
                click_largo ={ click_largo_elemento(indice)})
        }


    }
}
