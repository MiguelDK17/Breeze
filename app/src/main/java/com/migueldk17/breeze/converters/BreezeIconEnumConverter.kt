package com.migueldk17.breeze.converters

import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.runtime.Composable
import com.github.migueldk17.breezeicons.icons.BreezeIcon
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

        BreezeIconsEnum.HOME_LINEAR.name -> {
            BreezeIcons.Linear.Building.HomeLinear
        }

        BreezeIconsEnum.FACEBOOK_LINEAR.name -> {
            BreezeIcons.Linear.Company.FacebookLinear
        }
        BreezeIconsEnum.GOOGLE_LINEAR.name -> {
            BreezeIcons.Linear.Company.GoogleLinear
        }
        BreezeIconsEnum.GOOGLE_PLAY_LINEAR.name -> {
            BreezeIcons.Linear.Company.GooglePlayLinear
        }
        BreezeIconsEnum.WHATSAPP_LINEAR.name -> {
            BreezeIcons.Linear.Company.WhatsappLinear
        }
        BreezeIconsEnum.WINDOWS_LINEAR.name -> {
            BreezeIcons.Linear.Company.WindowsLinear
        }
        BreezeIconsEnum.ANDROID_LINEAR.name -> {
            BreezeIcons.Linear.Company.AndroidLinear
        }
        BreezeIconsEnum.SPOTIFY_LINEAR.name -> {
            BreezeIcons.Linear.Company.SpotifyLinear
        }

        BreezeIconsEnum.DOCUMENT_LINEAR.name -> {
            BreezeIcons.Linear.ContentEdit.DocumentLinear
        }

        BreezeIconsEnum.MAGIC_PEN_LINEAR.name -> {
            BreezeIcons.Linear.DesignTools.MagicPen
        }

        BreezeIconsEnum.GROUP_LINEAR.name -> {
            BreezeIcons.Linear.Delivery.GroupLinear
        }

        BreezeIconsEnum.FILE_TEXT_LINEAR.name -> {
            BreezeIcons.Linear.Files.FileText
        }

        BreezeIconsEnum.LIKE_LINEAR.name -> {
            BreezeIcons.Linear.SupportLikeQuestion.LikeLinear
        }

        BreezeIconsEnum.DOLLAR_SQUARE_LINEAR.name -> {
            BreezeIcons.Linear.Money.DollarSquare
        }

        BreezeIconsEnum.VERIFIED_CHECK.name -> {
            BreezeIcons.Linear.Money.VerifiedCheck
        }
        BreezeIconsEnum.NOTIFICATION_LINEAR.name -> {
            BreezeIcons.Linear.Notification.NotificationLinear
        }
        BreezeIconsEnum.NOTIFICATION_BING_LINEAR.name -> {
            BreezeIcons.Linear.Notification.NotificationBingLinear
        }
        BreezeIconsEnum.CALENDAR_LINEAR.name -> {
            BreezeIcons.Linear.Time.CalendarLinear
        }
        BreezeIconsEnum.FORWARD_PLAYER_LINEAR.name -> {
            BreezeIcons.Linear.VideoAudioImage.ForwardLinear
        }

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
        BreezeIcons.Linear.Money.DollarCircle.enum.name -> {
            BreezeIcons.Linear.Money.DollarCircle
        }
        BreezeIcons.Linear.Money.MoneySend.enum.name -> {
            BreezeIcons.Linear.Money.MoneySend
        }
        BreezeIcons.Linear.Money.MoneyRecive.enum.name -> {
            BreezeIcons.Linear.Money.MoneyRecive
        }

        BreezeIcons.Linear.Arrows.AltArrowLeft.enum.name -> {
            BreezeIcons.Linear.Arrows.AltArrowLeft
        }

        BreezeIcons.Linear.Arrows.AltArrowUp.enum.name -> {
            BreezeIcons.Linear.Arrows.AltArrowUp
        }
        BreezeIcons.Linear.Arrows.AltArrowDown.enum.name -> {
            BreezeIcons.Linear.Arrows.AltArrowDown
        }
        BreezeIcons.Linear.Arrows.AltArrowRight.enum.name -> {
            BreezeIcons.Linear.Arrows.AltArrowRight
        }

        BreezeIcons.Linear.Arrows.Refresh.enum.name -> {
            BreezeIcons.Linear.Arrows.Refresh
        }

        BreezeIcons.Linear.Arrows.Restart.enum.name -> {
            BreezeIcons.Linear.Arrows.Restart
        }

        BreezeIcons.Linear.Nature.ChristmasTree.enum.name -> {
            BreezeIcons.Linear.Nature.ChristmasTree
        }
        BreezeIcons.Linear.Nature.Recycle.enum.name -> {
            BreezeIcons.Linear.Nature.Recycle
        }

        BreezeIcons.Linear.Nature.Umbrella.enum.name -> {
            BreezeIcons.Linear.Nature.Umbrella
        }

        BreezeIcons.Linear.FoodKitchen.TeaCup.enum.name -> {
            BreezeIcons.Linear.FoodKitchen.TeaCup
        }
        BreezeIcons.Linear.Essetional.Hanger.enum.name -> {
            BreezeIcons.Linear.Essetional.Hanger
        }
        BreezeIcons.Linear.Essetional.ConfettiMinimalistic.enum.name -> {
            BreezeIcons.Linear.Essetional.ConfettiMinimalistic
        }
        BreezeIcons.Linear.Essetional.Sleeping.enum.name -> {
            BreezeIcons.Linear.Essetional.Sleeping
        }
        BreezeIcons.Linear.ElectronicDevices.Laptop.enum.name -> {
            BreezeIcons.Linear.ElectronicDevices.Laptop
        }
        BreezeIcons.Linear.ElectronicDevices.Gamepad.enum.name -> {
            BreezeIcons.Linear.ElectronicDevices.Gamepad
        }

        BreezeIcons.Linear.Essetional.TShirt.enum.name -> {
            BreezeIcons.Linear.Essetional.TShirt
        }
        BreezeIcons.Linear.Essetional.Skirt.enum.name -> {
            BreezeIcons.Linear.Essetional.Skirt
        }
        BreezeIcons.Linear.Like.Heart.enum.name -> {
            BreezeIcons.Linear.Like.Heart
        }

        BreezeIcons.Linear.School.CaseRoundMinimalistic.enum.name -> {
            BreezeIcons.Linear.School.CaseRoundMinimalistic
        }

        BreezeIcons.Linear.School.SquareAcademicCap2.enum.name -> {
            BreezeIcons.Linear.School.SquareAcademicCap2
        }





        else -> {
            BreezeIcons.Unspecified.IconUnspecified
        }
    }
    return icon
}