package com.migueldk17.breeze.ui.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.migueldk17.breeze.R

//Object que usa os icones
object BreezeIcons {

    //Icones Lineares
    object Linear {
        val BookLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_book))

        val GroupLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_group))

        val CarLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_car))

        val CloudLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_cloud))

        val GlobeLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_global))

        val AirplaneLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_airplane))

        val DiscoverLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_discover))

        val DropLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_drop))

        val KeyLinear: BreezeIconsType
            @Composable
            get() = BreezeIconsType.LinearIcon(ImageVector.vectorResource(R.drawable.ic_key))
    }

    //Icones de Cores
    object Colors {
        val IconOrange: BreezeIconsType
            @Composable
            get() = BreezeIconsType.ColorIcon(ImageVector.vectorResource(R.drawable.icon_orange))

        val IconYellow: BreezeIconsType
            @Composable
            get() = BreezeIconsType.ColorIcon(ImageVector.vectorResource(R.drawable.icon_yellow))

        val IconGreen: BreezeIconsType
            @Composable
            get() = BreezeIconsType.ColorIcon(ImageVector.vectorResource(R.drawable.icon_green))

        val IconGreenCyan: BreezeIconsType
            @Composable
            get() = BreezeIconsType.ColorIcon(ImageVector.vectorResource(R.drawable.icon_green_cyan))

        val IconTurquoise: BreezeIconsType
            @Composable
            get() = BreezeIconsType.ColorIcon(ImageVector.vectorResource(R.drawable.icon_turquoise))


    }




}