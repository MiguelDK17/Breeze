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
        BreezeIcons.Linear.VideoAudioImage.VideoCircleLinear.enum.name -> {
            BreezeIcons.Linear.VideoAudioImage.VideoCircleLinear
        }
        BreezeIcons.Linear.VideoAudioImage.ForwardLinear.enum.name -> {
            BreezeIcons.Linear.VideoAudioImage.ForwardLinear
        }
        BreezeIcons.Linear.Messages.ChatLinear.enum.name -> {
            BreezeIcons.Linear.Messages.ChatLinear
        }
        BreezeIcons.Linear.Shop.Bag2.enum.name -> {
            BreezeIcons.Linear.Shop.Bag2
        }
        BreezeIcons.Linear.Settings.SettingsLinear.enum.name -> {
            BreezeIcons.Linear.Settings.SettingsLinear
        }
        BreezeIcons.Linear.Mobility.BusLinear.enum.name -> {
            BreezeIcons.Linear.Mobility.BusLinear
        }
        BreezeIcons.Linear.Mobility.GasStationLinear.enum.name -> {
            BreezeIcons.Linear.Mobility.GasStationLinear
        }
        BreezeIcons.Linear.Building.Hospital.enum.name -> {
            BreezeIcons.Linear.Building.Hospital
        }
        BreezeIcons.Linear.ElectronicDevices.Airpods.enum.name -> {
            BreezeIcons.Linear.ElectronicDevices.Airpods
        }
        BreezeIcons.Linear.ElectronicDevices.HeadphonesRound.enum.name -> {
            BreezeIcons.Linear.ElectronicDevices.HeadphonesRound
        }
        BreezeIcons.Linear.Files.FileText.enum.name -> {
            BreezeIcons.Linear.Files.FileText
        }

        else -> {
            BreezeIcons.Unspecified.IconUnspecified
        }
    }
    return icon
}