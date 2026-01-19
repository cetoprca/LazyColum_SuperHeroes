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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazycolum_superheroes.model.SuperHeroe
import com.example.lazycolum_superheroes.ui.theme.LazyColumn_SuperHeroes_composeTheme
import com.example.lazycolum_superheroes.viewmodel.SuperHeroeViewModel

import com.example.recycler_superheroes_compose.ui.components.BottomNavigationBar_SuperHeroe
import com.example.recycler_superheroes_compose.ui.components.ElementoLazySuperHeroe
import com.example.recycler_superheroes_compose.ui.components.TopAppBar_Normal



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

           LazyColumn_SuperHeroes_composeTheme {

                val superheroeViewModel: SuperHeroeViewModel = viewModel()

                //statusBarPadding() deja la barra de estado libre para que
                //no se aplique el color del TopBar
                Scaffold(modifier = Modifier.fillMaxSize().statusBarsPadding(), topBar ={ TopAppBar_Normal(Modifier)}, bottomBar = {BottomNavigationBar_SuperHeroe()}) { innerPadding ->
                    ZonaCentral(Modifier.padding(innerPadding),superheroeViewModel.superHeroes){
                        superheroeViewModel.borrarHeroe(it)
                    }
                }
            }
        }
    }
}



@Composable
fun ZonaCentral(modificador: Modifier= Modifier, heroes:List<SuperHeroe>, borrar_superheroe:(SuperHeroe)->Unit)
{

    LazyColumn(modifier = modificador.fillMaxSize()) {
        //Mostramos los elementos
        items(heroes){
            ElementoLazySuperHeroe(it, Modifier){
                borrar_superheroe(it)
            }
        }


    }
}
