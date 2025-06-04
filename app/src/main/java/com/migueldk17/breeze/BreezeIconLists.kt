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
        BreezeIcons.Linear.Security.KeyLinear,
        BreezeIcons.Linear.VideoAudioImage.VideoCircleLinear,
        BreezeIcons.Linear.VideoAudioImage.ForwardLinear,
        BreezeIcons.Linear.Messages.ChatLinear,
        BreezeIcons.Linear.Shop.Bag2,
        BreezeIcons.Linear.Settings.SettingsLinear,
        BreezeIcons.Linear.Mobility.BusLinear,
        BreezeIcons.Linear.Mobility.GasStationLinear,
        BreezeIcons.Linear.Building.Hospital,
        BreezeIcons.Linear.ElectronicDevices.Airpods,
        BreezeIcons.Linear.ElectronicDevices.HeadphonesRound,
        BreezeIcons.Linear.Files.FileText
    )
    //Lista de icones de cores de tipo BreezeIcons
    @Composable
    fun getVibrantColorIcons() = listOf(
        BreezeIcons.Colors.Vibrant.IconRed,
        BreezeIcons.Colors.Vibrant.IconOrange,
        BreezeIcons.Colors.Vibrant.IconYellow,
        BreezeIcons.Colors.Vibrant.IconGreen,
        BreezeIcons.Colors.Vibrant.IconGreenCyan,
        BreezeIcons.Colors.Vibrant.IconTurquoise,
        BreezeIcons.Colors.Vibrant.IconBlue,
        BreezeIcons.Colors.Vibrant.RoyalBlue,
        BreezeIcons.Colors.Vibrant.IconPurple,
        BreezeIcons.Colors.Vibrant.IconMagenta,
        BreezeIcons.Colors.Vibrant.IconPink
    )

    @Composable
    fun getSoftColorIcons() = listOf(
            BreezeIcons.Colors.Soft.SoftRed,
            BreezeIcons.Colors.Soft.SoftOrange,
            BreezeIcons.Colors.Soft.SoftYellow,
            BreezeIcons.Colors.Soft.SoftGreen,
            BreezeIcons.Colors.Soft.SoftGreenCyan,
            BreezeIcons.Colors.Soft.SoftBlue,
            BreezeIcons.Colors.Soft.SoftRoyalBlue,
            BreezeIcons.Colors.Soft.SoftPurple,
            BreezeIcons.Colors.Soft.SoftMagenta,
            BreezeIcons.Colors.Soft.SoftPink
        )
}