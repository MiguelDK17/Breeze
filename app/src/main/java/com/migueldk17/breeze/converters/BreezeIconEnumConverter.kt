package com.migueldk17.breeze.converters

import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType

//Converter Manual de BreezeIconsEnum para String para evitar bugs com o Room e o KSP
fun BreezeIconsEnum.toDatabaseValue(): String {
    return this.name
}

//Converte String para BreezeIconsType
@Composable
fun String.toBreezeIconsType() : BreezeIconsType {
    val icon = when(this){

        BreezeIconsEnum.BOOK_LINEAR.name -> {
            BreezeIcons.Linear.SchoolLearning.BookLinear
        }

        BreezeIconsEnum.GROUP_LINEAR.name -> {
            BreezeIcons.Linear.Delivery.GroupLinear
        }

        BreezeIconsEnum.CAR_LINEAR.name -> {
            BreezeIcons.Linear.Mobility.CarLinear
        }

        BreezeIconsEnum.CLOUD_LINEAR.name -> {
            BreezeIcons.Linear.Weather.CloudLinear
        }

        BreezeIconsEnum.GLOBE_LINEAR.name -> {
            BreezeIcons.Linear.Location.GlobeLinear
        }

        BreezeIconsEnum.AIRPLANE_LINEAR.name -> {
            BreezeIcons.Linear.Mobility.AirplaneLinear
        }

        BreezeIconsEnum.DISCOVER_LINEAR.name -> {
            BreezeIcons.Linear.Essetional.DiscoverLinear
        }

        BreezeIconsEnum.DROP_LINEAR.name -> {
            BreezeIcons.Linear.Weather.DropLinear
        }

        BreezeIconsEnum.KEY_LINEAR.name -> {
            BreezeIcons.Linear.Security.KeyLinear
        }

        else -> {
            BreezeIcons.Unspecified.IconUnspecified
        }
    }
    return icon
}