package com.example.lazycolum_superheroes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lazycolum_superheroes.model.SuperHeroe
import com.example.lazycolum_superheroes.ui.theme.OnOrangePrimary
import com.example.lazycolum_superheroes.ui.theme.OnOrangeSecondary
import com.example.lazycolum_superheroes.ui.theme.OnOrangeTertiary
import com.example.lazycolum_superheroes.ui.theme.OrangePrimary
import com.example.lazycolum_superheroes.ui.theme.OrangeSecondary
import com.example.lazycolum_superheroes.ui.theme.OrangeTertiary

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DialogoSuperHeroe(
    superHeroe: SuperHeroe,
    onDismiss: () -> Unit,
    onGuardar: (SuperHeroe) -> Unit
) {
    var nombre by remember { mutableStateOf(superHeroe.nombre) }
    var publicador by remember { mutableStateOf(superHeroe.publicador) }

    Dialog(onDismissRequest = onDismiss) {
        // Fondo principal del dialogo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(
                    color = OrangeSecondary.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen
                GlideImage(
                    model = superHeroe.foto,
                    contentDescription = superHeroe.superHeroe,
                    modifier = Modifier
                        .size(150.dp)
                        .background(OrangePrimary, shape = RoundedCornerShape(12.dp))
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre editable
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedTextColor = OnOrangeSecondary,
                        unfocusedTextColor = OnOrangeSecondary,
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = OrangeTertiary,
                        cursorColor = OrangePrimary,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = OrangeTertiary,
                        focusedPlaceholderColor = OrangeTertiary,
                        unfocusedPlaceholderColor = OrangeTertiary

                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Publicador editable
                OutlinedTextField(
                    value = publicador,
                    onValueChange = { publicador = it },
                    label = { Text("Publicador") },
                    colors = OutlinedTextFieldDefaults.colors(

                        focusedTextColor = OnOrangeSecondary,
                        unfocusedTextColor = OnOrangeSecondary,
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = OrangeTertiary,
                        cursorColor = OrangePrimary,
                        focusedLabelColor = OrangePrimary,
                        unfocusedLabelColor = OrangeTertiary,
                        focusedPlaceholderColor = OrangeTertiary,
                        unfocusedPlaceholderColor = OrangeTertiary

                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onGuardar(superHeroe.apply {
                            this.nombre = nombre
                            this.publicador = publicador
                        }) },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary)
                    ) {
                        Text("Guardar", color = OnOrangePrimary, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = OrangeTertiary)
                    ) {
                        Text("Cancelar", color = OnOrangeTertiary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

