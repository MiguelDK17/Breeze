package com.migueldk17.breeze.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = SkyBlue,                  //Cor principal do app
    secondary = Blue,                   //Cor secundária do app
    background = Branco,                //Fundo principal
    surface = PastelLightBlue,          //Fundo de superfícies e cards
    onPrimary = Branco,                 //Texto e ícones sobre a cor principal
    onSecondary = Branco,               //Texto e ícones sobre a cor secundária
    onBackground = NavyBlue,            //Texto principal no fundo branco
    onSurface = NavyBlue                //Texto em superfícies

)

private val DarkColorScheme = darkColorScheme(
    primary = DeepSkyBlue,
    secondary = NavyPetrol,
    background = DarkBlue,
    surface = MidnightBlue,
    onPrimary = Branco,
    onSecondary = Branco,
    onBackground = LightGrayText,
    onSurface = LightGrayText
)

@Composable
fun BreezeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}