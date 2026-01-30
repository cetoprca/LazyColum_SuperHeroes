package com.example.recycler_superheroes_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lazycolum_superheroes.model.SuperHeroe

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ElementoLazySuperHeroe(misuperheroe: SuperHeroe, modificador: Modifier= Modifier,selecionado:()->Boolean, favorito:()->Boolean, click_borrar:()->Unit,click_largo:()->Unit,click_corto:()->Unit, click_favorito:()->Unit, action_mode: Boolean)
{

    val color_fondo=if(selecionado()) MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f) else MaterialTheme.colorScheme.secondary

    Card(modifier = modificador.combinedClickable(onLongClick = click_largo, onClick =click_corto ).padding(vertical = 4.dp, horizontal = 8.dp).height(160.dp).fillMaxWidth(),
        RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)){
        Row (modifier = Modifier.fillMaxWidth().background(color_fondo), verticalAlignment = Alignment.CenterVertically){

            GlideImage(misuperheroe.foto, modifier = Modifier.size(DpSize(150.dp,150.dp)).padding(start = 4.dp), contentDescription = "")
            Spacer(Modifier.width(8.dp))
            Column ( horizontalAlignment =Alignment.CenterHorizontally, modifier = Modifier.weight(1f)){
                Text(misuperheroe.superHeroe, fontWeight = FontWeight.Bold, fontSize = 21.sp, modifier = Modifier.padding(top = 4.dp))
                Text(misuperheroe.nombre, fontSize =14.sp , modifier = Modifier.padding(top = 8.dp))
                Text(misuperheroe.publicador, fontSize = 14.sp, modifier = Modifier.padding(top=4.dp))
                //ocupa todo el espacio restante, necesario que el Colum ocupe todo el tamaño en alto que le permita el card
                Spacer(modifier = Modifier.weight(1f))

                //He pasado action_mode hasta la tarjeta de superheroe para determinar si el boton de borrar está habilitado o no
                Button(enabled = !action_mode, onClick = {click_borrar()}, modifier = Modifier.padding(bottom = 2.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)) {
                    Text("BORRAR")
                }
            }
            IconButton(
                onClick = { click_favorito() }
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Añadir Favorito",
                    tint = if (favorito()) Color.Red else Color.Black
                )
            }
        }

    }
}

@Preview
@Composable
fun mostrar_elementoLazy()
{
    ElementoLazySuperHeroe(
        SuperHeroe(
            "Spiderman",
            "Marvel",
            "Peter Parker",
            "https://cursokotlin.com/wp-content/uploads/2017/07/spiderman.jpg"
        ), click_corto = {}, click_largo = {}, click_borrar = {}, click_favorito = {},

        selecionado = { true },
        favorito = {false},
        action_mode = false
    )
}