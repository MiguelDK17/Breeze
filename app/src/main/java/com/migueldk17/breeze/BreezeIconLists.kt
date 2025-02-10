package com.migueldk17.breeze

import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcons

object BreezeIconLists {
    //Lista de icones de tipo BreezeIcons
    @Composable
    fun getLinearIcons() = listOf(
        BreezeIcons.Linear.BookLinear,
        BreezeIcons.Linear.GroupLinear,
        BreezeIcons.Linear.GlobeLinear,
        BreezeIcons.Linear.CarLinear,
        BreezeIcons.Linear.CloudLinear,
        BreezeIcons.Linear.DropLinear,
        BreezeIcons.Linear.AirplaneLinear,
        BreezeIcons.Linear.DiscoverLinear,
        BreezeIcons.Linear.KeyLinear
    )
    //Lista de icones de cores de tipo BreezeIcons
    @Composable
    fun getColorIcons() = listOf(
        BreezeIcons.Colors.IconOrange,
        BreezeIcons.Colors.IconYellow,
        BreezeIcons.Colors.IconGreen,
        BreezeIcons.Colors.IconGreenCyan,
        BreezeIcons.Colors.IconTurquoise
    )
}