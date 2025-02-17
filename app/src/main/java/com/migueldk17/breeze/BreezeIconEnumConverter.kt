package com.migueldk17.breeze

import androidx.compose.runtime.Composable
import androidx.room.TypeConverter
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType

fun BreezeIconsEnum.toDatabaseValue(): String {
    return this.name
}

@Composable
fun BreezeIconsEnum.toBreezeIconsType(enum: BreezeIconsEnum) : BreezeIconsType {
    val icon = when(enum.name){

        BreezeIconsEnum.BOOK_LINEAR.name -> {
            BreezeIcons.Linear.BookLinear
        }

        BreezeIconsEnum.GROUP_LINEAR.name -> {
            BreezeIcons.Linear.GroupLinear
        }

        BreezeIconsEnum.CAR_LINEAR.name -> {
            BreezeIcons.Linear.CarLinear
        }

        BreezeIconsEnum.CLOUD_LINEAR.name -> {
            BreezeIcons.Linear.CloudLinear
        }

        BreezeIconsEnum.GLOBE_LINEAR.name -> {
            BreezeIcons.Linear.GlobeLinear
        }

        BreezeIconsEnum.AIRPLANE_LINEAR.name -> {
            BreezeIcons.Linear.AirplaneLinear
        }

        BreezeIconsEnum.DISCOVER_LINEAR.name -> {
            BreezeIcons.Linear.DiscoverLinear
        }

        BreezeIconsEnum.DROP_LINEAR.name -> {
            BreezeIcons.Linear.DropLinear
        }

        BreezeIconsEnum.KEY_LINEAR.name -> {
            BreezeIcons.Linear.KeyLinear
        }

        else -> {
            BreezeIcons.Unspecified.IconUnspecified
        }
    }
    return icon
}