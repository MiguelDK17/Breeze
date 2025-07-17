package com.migueldk17.breeze.ui.features.historico.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
@Preview(showBackground = true)
fun SemReceitasHistorico(){
    BreezeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                animationRes = com.migueldk17.breeze.R.raw.piggy_saving_money,
                isPlaying = true,
                iterations = 10,
                modifier = Modifier
                    .widthIn(min = 200.dp)
                    .heightIn(min = 200.dp, max = 500.dp)
            )
            TitleText(
                "Sem Receitas por Aqui!",
                fontWeight = FontWeight.Bold,
                size = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            DescriptionText(
                "Ainda não há nenhuma receita cadastrada neste mês!",
                fontWeight = FontWeight.Bold,
                size = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            DescriptionText(
                "Você pode começar adicionando uma na Página Inicial 💰!",
                fontWeight = FontWeight.Bold,
                size = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            BreezeButton(
                text = "Adicionar Receita",
                onClick = {}
            )
        }
    }
}