package com.migueldk17.breeze.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.Screen

@Composable
fun BreezeBottomBar(navController: NavController){
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar{
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Página Inicial") },
            label = { Text("Página Inicial") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                navController.navigate(Screen.PaginaInicial.route)
            }
        )
        NavigationBarItem(
            icon = { FaIcon(FaIcons.CalendarRegular) },
            label = { Text("Histórico") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                navController.navigate(Screen.Historico.route)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "") },
            label = { Text("Configurações") },
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                navController.navigate(Screen.Configuracoes.route)
            }
        )
    }
}

@Composable
fun BreezeBottomBarModel(){
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar{
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Página Inicial") },
            label = { Text("Página Inicial") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
            }
        )
        NavigationBarItem(
            icon = { FaIcon(FaIcons.CalendarRegular) },
            label = { Text("Histórico") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "") },
            label = { Text("Configurações") },
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
            }
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun Preview(){
    BreezeBottomBarModel()
}