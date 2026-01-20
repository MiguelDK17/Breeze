package com.migueldk17.breeze

import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcons

object BreezeIconLists {
    //Lista de icones de tipo BreezeIcons
    @Composable
    fun getLinearIcons() = listOf(
        // Building
        BreezeIcons.Linear.Building.Hospital,
        BreezeIcons.Linear.Building.HomeLinear,

        //Company
        BreezeIcons.Linear.Company.FacebookLinear,
        BreezeIcons.Linear.Company.WhatsappLinear,
        BreezeIcons.Linear.Company.GoogleLinear,
        BreezeIcons.Linear.Company.GooglePlayLinear,
        BreezeIcons.Linear.Company.AndroidLinear,
        BreezeIcons.Linear.Company.WindowsLinear,
        BreezeIcons.Linear.Company.SpotifyLinear,

        // Delivery
        BreezeIcons.Linear.Delivery.GroupLinear,

        // ElectronicDevices
        BreezeIcons.Linear.ElectronicDevices.Airpods,
        BreezeIcons.Linear.ElectronicDevices.HeadphonesRound,
        BreezeIcons.Linear.ElectronicDevices.Laptop,
        BreezeIcons.Linear.ElectronicDevices.Gamepad,

        // Essetional
        BreezeIcons.Linear.Essetional.DiscoverLinear,
        BreezeIcons.Linear.Essetional.Hanger,
        BreezeIcons.Linear.Essetional.Skirt,
        BreezeIcons.Linear.Essetional.TShirt,
        BreezeIcons.Linear.Essetional.ConfettiMinimalistic,
        BreezeIcons.Linear.Essetional.Sleeping,

        // Files
        BreezeIcons.Linear.Files.FileText,

        // FoodKitchen
        BreezeIcons.Linear.FoodKitchen.TeaCup,

        // Location
        BreezeIcons.Linear.Location.GlobeLinear,

        // Messages
        BreezeIcons.Linear.Messages.ChatLinear,

        // Mobility
        BreezeIcons.Linear.Mobility.AirplaneLinear,
        BreezeIcons.Linear.Mobility.BusLinear,
        BreezeIcons.Linear.Mobility.CarLinear,
        BreezeIcons.Linear.Mobility.GasStationLinear,

        // SchoolLearning
        BreezeIcons.Linear.SchoolLearning.BookLinear,

        // Security
        BreezeIcons.Linear.Security.KeyLinear,

        // Settings
        BreezeIcons.Linear.Settings.SettingsLinear,

        // Shop
        BreezeIcons.Linear.Shop.Bag2,

        // VideoAudioImage
        BreezeIcons.Linear.VideoAudioImage.ForwardLinear,
        BreezeIcons.Linear.VideoAudioImage.VideoCircleLinear,

        // Weather
        BreezeIcons.Linear.Weather.CloudLinear,
        BreezeIcons.Linear.Weather.DropLinear,
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