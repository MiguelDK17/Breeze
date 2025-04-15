package com.migueldk17.breeze.ui.features.paginainicial.ui.layouts

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieConstants
import com.migueldk17.breeze.MainActivity3
import com.migueldk17.breeze.R
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.theme.BreezeTheme


@Composable
fun ContaNaoEncontrada(){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            animationRes = R.raw.animation_account_not_found, //Recurso de animação JSON
            isPlaying = true,
            iterations = LottieConstants.IterateForever,    //Irá tocar pra sempre
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Nenhuma conta encontrada!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Que tal criar uma nova conta agora mesmo ?",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val intent = Intent(context, MainActivity3::class.java)
            context.startActivity(intent)
        },
             modifier = Modifier
                 .padding(horizontal = 16.dp)
                 .height(50.dp)) {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Conta",
                modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Criar Conta")
        }
    }
}

@Composable
@Preview(showBackground = false)
private fun Preview(){
    BreezeTheme {
        ContaNaoEncontrada()
    }
}