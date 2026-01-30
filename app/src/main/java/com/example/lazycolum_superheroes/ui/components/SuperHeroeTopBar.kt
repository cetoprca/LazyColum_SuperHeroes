package com.example.recycler_superheroes_compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar_Normal(modifier: Modifier = Modifier)
{
    var menu_expandido by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text("Lazy_SuperHeroes")
        },
        actions = {
            IconButton(onClick = {menu_expandido=!menu_expandido}) {
                Icon(Icons.Default.MoreVert, contentDescription = "")
            }
            //Ahora se incluye el menu de opciones
            Menu_opciones(menu_expandido,{menu_expandido=false}, opcion_click = {menu_expandido=false})
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
    ),
       )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar_ActionMode(modifier: Modifier,  elementos_seleccionados:Int,click_atras:()->Unit,click_eliminar:()->Unit,click_editar: ()->Unit)
{
    TopAppBar(
        title = {Text("Elementos seleccionados $elementos_seleccionados", fontSize = 16.sp)},
        navigationIcon = {
            IconButton(onClick = click_atras) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Salir")
            }
        },
        actions = {
            if(elementos_seleccionados==1) {//Hay solo hay un elemento seleccionado añado el Edit
                IconButton(onClick =click_editar) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
            }

                IconButton(onClick = click_eliminar) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }

        }
        )
}

@Composable
fun Menu_opciones(expandido: Boolean,ondismis:()->Unit,opcion_click:()->Unit)
{
    DropdownMenu(expanded = expandido, onDismissRequest = {ondismis() }, shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray)) {
        DropdownMenuItem(text = {Text("Settings")}, leadingIcon = {Icon(Icons.Default.Settings,"")}, onClick = {opcion_click()})

    }
}