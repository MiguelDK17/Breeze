package com.migueldk17.breeze.ui.components


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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
                    selectedIconColor = Color.Transparent,
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
                    selectedIconColor = Color.Unspecified,
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
                    selectedIconColor = Color.Transparent,
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
    val home = BreezeIcons.Linear.Building.HomeLinear
    val settings = BreezeIcons.Linear.Settings.SettingsLinear

    NavigationBar{
        NavigationBarItem(
            icon = { BreezeIcon(home, contentDescription = "Página Inicial") },
            label = { Text("Página Inicial") },
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
            }
        )
        NavigationBarItem(
            icon = { BreezeIcon(BreezeIcons.Linear.Time.CalendarLinear, contentDescription = "Histórico") },
            label = { Text("Histórico") },
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
            }
        )
        NavigationBarItem(
            icon = { BreezeIcon(settings, contentDescription = "Configurações")},
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