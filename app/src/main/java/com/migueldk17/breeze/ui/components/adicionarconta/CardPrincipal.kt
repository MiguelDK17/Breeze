package com.migueldk17.breeze.ui.components.adicionarconta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.SkyBlue

@Composable
fun CardPrincipal(
    content: @Composable ColumnScope.() -> Unit
    ) {

    Card(
        modifier = Modifier
            .size(width = 383.dp, height = 571.dp)
            .padding(5.dp),
        border = BorderStroke(1.dp, SkyBlue),
        shape = RoundedCornerShape(10.dp),
        colors = CardColors(
            contentColor = Color.Transparent,
            containerColor = PastelLightBlue,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

