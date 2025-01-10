package com.migueldk17.breeze.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.migueldk17.breeze.R

object BreezeIcons {

    val BookLinear: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_book)

    val GroupLinear: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_group)

    val CarLinear: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_car)

    val CloudLinear: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_cloud)

    val GlobeLinear: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_global)
}