package com.example.recycler_superheroes_compose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun     BottomNavigationBar_SuperHeroe(
    navController : NavController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar() {
        NavigationBarItem(
            selected = currentRoute == "inicio",
            onClick = {
                navController.navigate("inicio"){
                    popUpTo("inicio"){saveState = true}
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = currentRoute == "favoritos",
            onClick = {
                navController.navigate("favoritos"){
                    popUpTo("inicio"){saveState = true}
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Fav") },
            label = { Text("Favoritos") }
        )
    }
}