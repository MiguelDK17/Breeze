package com.migueldk17.breeze.ui.icons


import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BreezeIcon(
    breezeIcon: BreezeIconsType,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    color: Color = Color.Unspecified
    ) {

    //Converte BreezeIconType para ImageVector e identifica qual o tipo de ícone
    val icon: ImageVector = when (breezeIcon) {
        is BreezeIconsType.ColorIcon -> breezeIcon.icon
        is BreezeIconsType.LinearIcon -> breezeIcon.icon
    }

    //Muda a cor do ícone conforme o tipo
    val tint = when {
        //Caso seja ColorIcon a cor é uma cor não especificada, para que a cor do icone seja mantida
        breezeIcon is BreezeIconsType.ColorIcon -> {
            Color.Unspecified // Mantém as cores originais
        }
        //Caso o icone seja Linear e a cor for passada como parametro da função a cor passada será aplicada ao icone
        breezeIcon is BreezeIconsType.LinearIcon && color != Color.Unspecified -> {
            color
        }
        //Caso o icone seja diferente de ColorIcon e nenhuma cor for passada como parametro da função a cor estabelecida será a onSurface
        else -> {
            MaterialTheme.colorScheme.onSurface
        }

    }
    //Icon padrão do Jetpack Compose com as definições já filtradas e estabelecidas
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}