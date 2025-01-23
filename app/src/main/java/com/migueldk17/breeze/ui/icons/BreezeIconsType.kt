package com.migueldk17.breeze.ui.icons

import androidx.compose.ui.graphics.vector.ImageVector

//Classe que é usada para tipar os icones
sealed class BreezeIconsType {
    //Icones Coloridos
    data class ColorIcon(val icon: ImageVector): BreezeIconsType()
    //Icones Lineares
    data class LinearIcon(val icon: ImageVector): BreezeIconsType()
}