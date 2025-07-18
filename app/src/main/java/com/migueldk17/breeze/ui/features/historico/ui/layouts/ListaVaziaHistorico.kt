package com.migueldk17.breeze.ui.features.historico.ui.layouts


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import kotlinx.coroutines.delay

@Composable
fun ListaVaziaHistorico(
    animationRes: Int,
    titleText: String,
    descriptionText1: String,
    descriptionText2: String,
    buttonText: String,
    onClick: () -> Unit
){
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }
    AnimatedVisibility(visible = showContent, enter = fadeIn() + slideInVertically()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                animationRes = animationRes,
                isPlaying = true,
                modifier = Modifier
                    .widthIn(min = 200.dp)
                    .heightIn(min = 200.dp, max = 500.dp)
            )
            TitleText(
                titleText,
                fontWeight = FontWeight.Bold,
                size = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            DescriptionText(
                descriptionText1,
                fontWeight = FontWeight.Bold,
                size = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            DescriptionText(
                descriptionText2,
                fontWeight = FontWeight.Bold,
                size = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            BreezeButton(
                text = buttonText,
                onClick = onClick
            )

        }
    }

}