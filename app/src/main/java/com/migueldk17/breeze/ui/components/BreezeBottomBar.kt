package com.migueldk17.breeze.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.Screen

@Composable
fun BreezeBottomBar(navController: NavController){
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar {
            NavigationBarItem(
                icon = {
                    BreezeIcon(
                        breezeIcon = BreezeIcons.Linear.Building.HomeLinear,
                        contentDescription = "Página Inicial"
                    )
                },
                label = { Text("Página Inicial") },
                selected = selectedItem == 0,
                onClick = {
                    selectedItem = 0
                    navController.navigate(Screen.PaginaInicial.route)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified,
                )
            )
            NavigationBarItem(
                icon = {
                    BreezeIcon(
                        BreezeIcons.Linear.Time.CalendarLinear,
                        contentDescription = null
                    )
                },
                label = { Text("Histórico") },
                selected = selectedItem == 1,
                onClick = {
                    selectedItem = 1
                    navController.navigate(Screen.Historico.route)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.Unspecified, //Cor do icone aqui não afeta icones da biblioteca FAIcon
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Unspecified,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified,
                )
            )
            NavigationBarItem(
                icon = {
                    BreezeIcon(
                        breezeIcon = BreezeIcons.Linear.Settings.SettingsLinear,
                        contentDescription = "Configurções"
                    )
                },
                label = { Text("Configurações") },
                selected = selectedItem == 2,
                onClick = {
                    selectedItem = 2
                    navController.navigate(Screen.Configuracoes.route)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified,
                )
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
            icon = { BreezeIcon(BreezeIcons.Linear.Time.CalendarLinear, contentDescription = null) },
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