package com.migueldk17.breeze.ui.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.features.paginainicial.navigation.routes.Screen
import com.migueldk17.breeze.ui.utils.navigateSingleTopTo

@Composable
fun BreezeBottomBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedItem = when(currentRoute){
        Screen.PaginaInicial.route -> 0
        Screen.Historico.route -> 1
        Screen.Configuracoes.route -> 2
        else -> 0
    }

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
                    navController.navigateSingleTopTo(route = Screen.PaginaInicial.route)
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
                    navController.navigateSingleTopTo(route = Screen.Historico.route)
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
                    navController.navigateSingleTopTo(route = Screen.Configuracoes.route)
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