package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.MediumGrey
import com.migueldk17.breeze.ui.theme.blackPoppinsDarkMode
import com.migueldk17.breeze.ui.theme.blackPoppinsLightMode
import com.migueldk17.breeze.ui.theme.greyTextMediumPoppinsDarkMode

@Composable
fun PersonalizationCard(
    corCard: Color = MaterialTheme.colorScheme.surface,
    corIcone: Color = MaterialTheme.colorScheme.onSurface,
    icone: BreezeIconsType = BreezeIcons.Unspecified.IconUnspecified,
    nomeConta: String = "",
    valorMascarado: String = ""
){
    Log.d(TAG, "PersonalizationCard: ${icone.enum}")
    Card(
        modifier = Modifier
            .size(width = 342.dp, height = 80.dp)
        ,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardColors(
            containerColor = corCard,
            contentColor = Color.Black,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )


    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            BreezeIcon(
                icone,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp),
                color = corIcone
            )
            Spacer(modifier = Modifier.size(15.dp))
            Column {
                Text(nomeConta,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = if (!isSystemInDarkTheme()) blackPoppinsLightMode else DeepSkyBlue
                    ),
                    modifier = Modifier.padding(5.dp))
                Text(valorMascarado,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = if (!isSystemInDarkTheme()) blackPoppinsLightMode else DeepSkyBlue
                    ),
                    modifier = Modifier.padding(5.dp))
            }
        }
    }
}