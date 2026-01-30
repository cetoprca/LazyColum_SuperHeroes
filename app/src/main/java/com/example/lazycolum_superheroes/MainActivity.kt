package com.example.lazycolum_superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lazycolum_superheroes.model.SuperHeroe
import com.example.lazycolum_superheroes.ui.components.DialogoSuperHeroe
import com.example.lazycolum_superheroes.ui.theme.LazyColumn_SuperHeroes_composeTheme
import com.example.lazycolum_superheroes.viewmodel.SuperHeroeViewModel
import com.example.recycler_superheroes_compose.ui.components.BottomNavigationBar_SuperHeroe
import com.example.recycler_superheroes_compose.ui.components.ElementoLazySuperHeroe
import com.example.recycler_superheroes_compose.ui.components.TopAppBar_ActionMode
import com.example.recycler_superheroes_compose.ui.components.TopAppBar_Normal


class MainActivity : ComponentActivity() {
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
fun Pantalla(viewModel : SuperHeroeViewModel = viewModel()) {

    val navController = rememberNavController()

    val actionMode by viewModel.actionMode.collectAsState()
    val mostrarDialogo by viewModel.mostrarDialogo.collectAsState()
    val selectedSuperHeroes by viewModel.selectedSuperHeroes.collectAsState()
    val favoriteSuperHeroes by viewModel.favoriteSuperHeroes.collectAsState()
    val superHeroes by viewModel.superheroes.collectAsState()


    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(), topBar ={
        //El ToolBar que se muestra depende si esta activado el action_mode
        if(!actionMode) {
            TopAppBar_Normal(Modifier)
        }
        else
        {
            TopAppBar_ActionMode(modifier = Modifier,
                selectedSuperHeroes.size,
                click_atras = {
                    //vacio la lista de seleccionados
                    viewModel.clearSeleccion()
                    viewModel.setActionMode(false)

                },
                click_editar = {
                    //ABrir un Dialog para editar el elemento seleccionado
                    //Se supone que solo tiene que haber un elemento seleccionado
                    if(selectedSuperHeroes.size==1)
                    {
                        viewModel.setMostrarDialogo(true)
                    }

                },
                click_eliminar = {
                    //Tengo que eliminar todos los elementos seleccionados
                    selectedSuperHeroes.forEach {
                        viewModel.borrarHeroe(it)
                    }
                    //Elimino la lista de seleccionados
                    viewModel.clearSeleccion()
                    //Cierro el action_mode
                    viewModel.setActionMode(false)
                })
        }
    },
        bottomBar = {BottomNavigationBar_SuperHeroe(navController)


        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio"
        ){
            composable(route = "inicio") {
                ZonaCentral(Modifier.padding(innerPadding),superHeroes,
                    borrar_superheroe = { viewModel.borrarHeroe(it) },
                    click_corto_elemento = { viewModel.seleccionarHeroe(it) },
                    click_largo_elemento = {heroe-> viewModel.seleccionarHeroe(heroe) },
                    esta_seleccionado = { selectedSuperHeroes.contains(it) },
                    click_favorito = { viewModel.toggleFavorito(it)},
                    es_favorito = { favoriteSuperHeroes.contains(it) },
                    action_mode = actionMode)
                if(mostrarDialogo)
                {
                    DialogoSuperHeroe(
                        selectedSuperHeroes[0],
                        onDismiss = {viewModel.setMostrarDialogo(false)},
                        onGuardar = { superheroe_actualizado->
                            //Guardo los datos del superheroe
                            viewModel.actualizarHeroe(selectedSuperHeroes[0],superheroe_actualizado)
                            //Cierro el dialogo
                            viewModel.setMostrarDialogo(false)
                            //Vacio la lista de seleccinados
                            viewModel.clearSeleccion()
                            //Salgo del action_mode
                            viewModel.setActionMode(false)
                        })
                }
            }

            composable(route = "favoritos") {
                ZonaCentral(Modifier.padding(innerPadding),favoriteSuperHeroes,
                    borrar_superheroe = { viewModel.borrarHeroe(it) },
                    click_corto_elemento = { viewModel.seleccionarHeroe(it) },
                    click_largo_elemento = { viewModel.seleccionarHeroe(it) },
                    esta_seleccionado = { selectedSuperHeroes.contains(it) },
                    click_favorito = { viewModel.toggleFavorito(it) },
                    es_favorito = { favoriteSuperHeroes.contains(it) },
                    action_mode = actionMode)
                if(mostrarDialogo)
                {
                    DialogoSuperHeroe(
                        selectedSuperHeroes[0],
                        onDismiss = {viewModel.setMostrarDialogo(false)},
                        onGuardar = { superheroe_actualizado->
                            //Guardo los datos del superheroe
                            viewModel.actualizarHeroe(selectedSuperHeroes[0],superheroe_actualizado)
                            //Cierro el dialogo
                            viewModel.setMostrarDialogo(false)
                            //Vacio la lista de seleccinados
                            viewModel.clearSeleccion()
                            //Salgo del action_mode
                            viewModel.setActionMode(false)
                        })
                }
            }
        }

    }
}

@Composable
fun ZonaCentral(modificador: Modifier= Modifier, heroes:List<SuperHeroe>, borrar_superheroe:(SuperHeroe)->Unit, click_corto_elemento:(SuperHeroe)->Unit, click_largo_elemento:(SuperHeroe)->Unit, click_favorito:(SuperHeroe)->Unit, esta_seleccionado:(SuperHeroe)->Boolean, es_favorito:(SuperHeroe)->Boolean, action_mode: Boolean)
{

    LazyColumn(modifier = modificador.fillMaxSize()) {
        //Mostramos los elementos
        itemsIndexed(heroes){indice,her->
            ElementoLazySuperHeroe(
                her,
                Modifier,
                selecionado = { esta_seleccionado(heroes[indice]) },
                favorito = { es_favorito(heroes[indice]) },
                click_borrar = { borrar_superheroe(her) },
                click_corto = { click_corto_elemento(heroes[indice]) },
                click_largo = { click_largo_elemento(heroes[indice]) },
                click_favorito = { click_favorito(heroes[indice]) },
                action_mode = action_mode
            )
        }
    }
}
