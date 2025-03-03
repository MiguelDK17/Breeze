package com.migueldk17.breeze

import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcons

object BreezeIconLists {
    //Lista de icones de tipo BreezeIcons
    @Composable
    fun getLinearIcons() = listOf(
        BreezeIcons.Linear.SchoolLearning.BookLinear,
        BreezeIcons.Linear.Delivery.GroupLinear,
        BreezeIcons.Linear.Location.GlobeLinear,
        BreezeIcons.Linear.Mobility.CarLinear,
        BreezeIcons.Linear.Weather.CloudLinear,
        BreezeIcons.Linear.Weather.DropLinear,
        BreezeIcons.Linear.Mobility.AirplaneLinear,
        BreezeIcons.Linear.Essetional.DiscoverLinear,
        BreezeIcons.Linear.Security.KeyLinear
    )
    //Lista de icones de cores de tipo BreezeIcons
    @Composable
    fun getColorIcons() = listOf(
        BreezeIcons.Colors.Vibrant.IconOrange,
        BreezeIcons.Colors.Vibrant.IconYellow,
        BreezeIcons.Colors.Vibrant.IconGreen,
        BreezeIcons.Colors.Vibrant.IconGreenCyan,
        BreezeIcons.Colors.Vibrant.IconTurquoise
    )
}