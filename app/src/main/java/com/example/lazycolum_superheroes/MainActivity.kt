package com.example.lazycolum_superheroes

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.collectAsState


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

    val action_mode by superheroeViewModel.action_mode.collectAsState()
    val mostrar_dialogo by superheroeViewModel.mostrar_dialogo.collectAsState()
    val selectedSuperHeroes by superheroeViewModel.selectedSuperHeroes.collectAsState()
    val superHeroes by superheroeViewModel.superheroes.collectAsState()


    //statusBarPadding() deja la barra de estado libre para que
    //no se aplique el color del TopBar
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(), topBar ={
        //El ToolBar que se muestra depende si esta activado el action_mode
        if(!action_mode) {
            TopAppBar_Normal(Modifier)
        }
        else
        {
            TopAppBar_ActionMode(modificador = Modifier,
                selectedSuperHeroes.size,
                click_atras = {
                    //vacio la lista de seleccionados
                    superheroeViewModel.clearSeleccion()
                    superheroeViewModel.setActionMode(false)

                },
                click_editar = {
                    //ABrir un Dialog para editar el elemento seleccionado
                    //Se supone que solo tiene que haber un elemento seleccionado
                    if(selectedSuperHeroes.size==1)
                    {
                        superheroeViewModel.setMostrarDialogo(true)
                    }

                },
                click_eliminar = {
                    //Tengo que eliminar todos los elementos seleccionados
                    selectedSuperHeroes.forEach {
                        superheroeViewModel.borrarHeroe(it)
                    }
                    //Elimino la lista de seleccionados
                    superheroeViewModel.clearSeleccion()
                    //Cierro el action_mode
                    superheroeViewModel.setActionMode(false)
                })
        }
        },
        bottomBar = {BottomNavigationBar_SuperHeroe()


        }) { innerPadding ->
        ZonaCentral(Modifier.padding(innerPadding),superHeroes,
            borrar_superheroe = {
                // Evitar que se pueda borrar usando el boton de la card si action mode está habilitado
                // Esto es un fallback en caso de que falle por algun motivo el deshabilitado del boton
            if (!action_mode){
                superheroeViewModel.borrarHeroe(it)
            }
        },
            click_corto_elemento = {
                if(action_mode)
                {
                    //Añado el elemento a la lista de seleccionados,si no esta seleccionado previamente
                    if(superheroeViewModel.isHeroeSeleccionado(it))
                    {
                        //El elemento ya se ha añadido a la lista lo que hago es eliminarlo
                        Log.i("INFO", "antes deselect ${System.identityHashCode(superheroeViewModel.selectedSuperHeroes.value)}")

                        superheroeViewModel.deseleccionarHeroe(it)

                        Log.i("INFO", "despues deselect ${System.identityHashCode(superheroeViewModel.selectedSuperHeroes.value)}")

                        //Si ademas ya no hay elementos seleccionados cierro el action_mode
                        if(superheroeViewModel.isSeleccionEmpty())
                            superheroeViewModel.setActionMode(false)
                    }
                    else {
                        //Si no lo añado

                        Log.i("INFO", "antes select ${System.identityHashCode(superheroeViewModel.selectedSuperHeroes.value)}")
                        superheroeViewModel.seleccionarHeroe(it)

                        Log.i("INFO", "despues select ${System.identityHashCode(superheroeViewModel.selectedSuperHeroes.value)}")
                    }
                }

            },
            click_largo_elemento = {heroe->
                if(!action_mode)
                {
                    superheroeViewModel.setActionMode(true)
                    superheroeViewModel.seleccionarHeroe(heroe)
                }
            },
            esta_seleccionado = {heroe->
                for (heroeinterno in superheroeViewModel.selectedSuperHeroes.value){
                    Log.i("Info", "${heroeinterno == heroe} ${heroeinterno.equals(heroe)}")
                }
                superheroeViewModel.selectedSuperHeroes.value.contains(heroe)
            },
            action_mode = action_mode)
        if(mostrar_dialogo)
        {
            DialogoSuperHeroe(
                selectedSuperHeroes[0],
                onDismiss = {superheroeViewModel.setMostrarDialogo(false)},
                onGuardar = { superheroe_actualizado->
                    //Guardo los datos del superheroe
                    superheroeViewModel.actualizarHeroe(selectedSuperHeroes[0],superheroe_actualizado)
                    //Cierro el dialogo
                    superheroeViewModel.setMostrarDialogo(false)
                    //Vacio la lista de seleccinados
                    superheroeViewModel.clearSeleccion()
                    //Salgo del action_mode
                    superheroeViewModel.setActionMode(false)
                })
        }
    }
}


@Composable
fun ZonaCentral(modificador: Modifier= Modifier, heroes:List<SuperHeroe>, borrar_superheroe:(SuperHeroe)->Unit, click_corto_elemento:(SuperHeroe)->Unit, click_largo_elemento:(SuperHeroe)->Unit, esta_seleccionado:(SuperHeroe)->Boolean, action_mode: Boolean)
{

    LazyColumn(modifier = modificador.fillMaxSize()) {
        //Mostramos los elementos
        itemsIndexed(heroes){indice,her->
            ElementoLazySuperHeroe(
                her,
                Modifier,
                selecionado = { esta_seleccionado(heroes[indice]) },
                click_borrar = { borrar_superheroe(her) },
                click_corto = { click_corto_elemento(heroes[indice]) },
                click_largo = { click_largo_elemento(heroes[indice]) },
                action_mode = action_mode
            )
        }


    }
}
