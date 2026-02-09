package com.migueldk17.breeze.converters


import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType

//Converter Manual de BreezeIconsEnum para String para evitar bugs com o Room e o KSP
fun BreezeIconsEnum.toDatabaseValue(): String {
    return this.name
}

@Composable
fun String.toBreezeIconsType(): BreezeIconsType {
    return when (this) {

        BreezeIconsEnum.HOME_LINEAR.name ->
            BreezeIcons.Linear.Building.HomeLinear

        BreezeIconsEnum.HOSPITAL_LINEAR.name ->
            BreezeIcons.Linear.Building.Hospital

        BreezeIconsEnum.FACEBOOK_LINEAR.name ->
            BreezeIcons.Linear.Company.FacebookLinear

        BreezeIconsEnum.WHATSAPP_LINEAR.name ->
            BreezeIcons.Linear.Company.WhatsappLinear

        BreezeIconsEnum.GOOGLE_LINEAR.name ->
            BreezeIcons.Linear.Company.GoogleLinear

        BreezeIconsEnum.GOOGLE_PLAY_LINEAR.name ->
            BreezeIcons.Linear.Company.GooglePlayLinear

        BreezeIconsEnum.ANDROID_LINEAR.name ->
            BreezeIcons.Linear.Company.AndroidLinear

        BreezeIconsEnum.WINDOWS_LINEAR.name ->
            BreezeIcons.Linear.Company.WindowsLinear

        BreezeIconsEnum.SPOTIFY_LINEAR.name ->
            BreezeIcons.Linear.Company.SpotifyLinear

        BreezeIconsEnum.GROUP_LINEAR.name ->
            BreezeIcons.Linear.Delivery.GroupLinear

        BreezeIconsEnum.AIRPODS_LINEAR.name ->
            BreezeIcons.Linear.ElectronicDevices.Airpods

        BreezeIconsEnum.HEADPHONES_ROUND_LINEAR.name ->
            BreezeIcons.Linear.ElectronicDevices.HeadphonesRound

        BreezeIconsEnum.LAPTOP_LINEAR.name ->
            BreezeIcons.Linear.ElectronicDevices.Laptop

        BreezeIconsEnum.GAMEPAD_LINEAR.name ->
            BreezeIcons.Linear.ElectronicDevices.Gamepad

        BreezeIconsEnum.DISCOVER_LINEAR.name ->
            BreezeIcons.Linear.Essetional.DiscoverLinear

        BreezeIconsEnum.HANGER_LINEAR.name ->
            BreezeIcons.Linear.Essetional.Hanger

        BreezeIconsEnum.SKIRT_LINEAR.name ->
            BreezeIcons.Linear.Essetional.Skirt

        BreezeIconsEnum.T_SHIRT_LINEAR.name ->
            BreezeIcons.Linear.Essetional.TShirt

        BreezeIconsEnum.CONFETTI_MINIMALISTIC_LINEAR.name ->
            BreezeIcons.Linear.Essetional.ConfettiMinimalistic

        BreezeIconsEnum.SLEEPING_LINEAR.name ->
            BreezeIcons.Linear.Essetional.Sleeping

        BreezeIconsEnum.FILE_TEXT_LINEAR.name ->
            BreezeIcons.Linear.Files.FileText

        BreezeIconsEnum.CHEF_HAT_LINEAR.name ->
            BreezeIcons.Linear.FoodKitchen.ChefHat

        BreezeIconsEnum.TEA_CUP_LINEAR.name ->
            BreezeIcons.Linear.FoodKitchen.TeaCup

        BreezeIconsEnum.GLOBE_LINEAR.name ->
            BreezeIcons.Linear.Location.GlobeLinear

        BreezeIconsEnum.CHAT_SQUARE_LINEAR.name ->
            BreezeIcons.Linear.Messages.ChatLinear

        BreezeIconsEnum.AIRPLANE_LINEAR.name ->
            BreezeIcons.Linear.Mobility.AirplaneLinear

        BreezeIconsEnum.BUS_LINEAR.name ->
            BreezeIcons.Linear.Mobility.BusLinear

        BreezeIconsEnum.CAR_LINEAR.name ->
            BreezeIcons.Linear.Mobility.CarLinear

        BreezeIconsEnum.GAS_STATION_LINEAR.name ->
            BreezeIcons.Linear.Mobility.GasStationLinear

        BreezeIconsEnum.CASE_ROUND_MINIMALISTIC_LINEAR.name ->
            BreezeIcons.Linear.School.CaseRoundMinimalistic

        BreezeIconsEnum.SQUARE_ACADEMIC_CAP_2_LINEAR.name ->
            BreezeIcons.Linear.School.SquareAcademicCap2

        BreezeIconsEnum.BOOK_LINEAR.name ->
            BreezeIcons.Linear.SchoolLearning.BookLinear

        BreezeIconsEnum.KEY_LINEAR.name ->
            BreezeIcons.Linear.Security.KeyLinear

        BreezeIconsEnum.SETTINGS_LINEAR.name ->
            BreezeIcons.Linear.Settings.SettingsLinear

        BreezeIconsEnum.BAG2_LINEAR.name ->
            BreezeIcons.Linear.Shop.Bag2

        BreezeIconsEnum.FORWARD_LINEAR.name ->
            BreezeIcons.Linear.VideoAudioImage.ForwardLinear

        BreezeIconsEnum.VIDEO_CIRCLE_LINEAR.name ->
            BreezeIcons.Linear.VideoAudioImage.VideoCircleLinear

        BreezeIconsEnum.CLOUD_LINEAR.name ->
            BreezeIcons.Linear.Weather.CloudLinear

        BreezeIconsEnum.DROP_LINEAR.name ->
            BreezeIcons.Linear.Weather.DropLinear

        else ->
            BreezeIcons.Unspecified.IconUnspecified
    }
}
