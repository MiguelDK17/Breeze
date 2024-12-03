package com.migueldk17.breeze.ui.layouts

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.MainActivity
import com.migueldk17.breeze.ui.animation.ColorTransitionFromCenter
import com.migueldk17.breeze.viewmodels.BreezeViewModel

@Composable
fun EditarValorConta(viewModel: BreezeViewModel, modifier: Modifier = Modifier){
    val cardColor = viewModel.cardColor.collectAsState().value
    Log.d(TAG, "EditarValorConta: $cardColor")
    val iconColor = viewModel.iconColor.collectAsState()
    val nome = viewModel.nomeConta.collectAsState()
    var text by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        //Animação de fundo
        ColorTransitionFromCenter(cardColor)

        Column(modifier = Modifier
        .fillMaxWidth()
            .height(450.dp)
        .padding(horizontal = 16.dp), //Margem lateral
         verticalArrangement = Arrangement.SpaceAround,   // Espaçamento proporcional
        horizontalAlignment = Alignment.CenterHorizontally) {
        IconColumn(iconColor.value, nome.value)

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp, // Sombra externa
                        shape = RoundedCornerShape(12.dp) // Mesma forma do Card
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardColor)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(12.dp)
                    )
            ){
                OutlinedCard(
                    modifier = Modifier
                        .size(width = 332.dp, height = 113.dp),
                    colors = CardColors(
                        containerColor = Color.Transparent.copy(alpha = 0.01f),
                        contentColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = Color.Unspecified,
                        disabledContainerColor = Color.Unspecified),
                    ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OutlinedTextField(text, onValueChange = { value ->
                                text = value
                            },
                                placeholder = {
                                    Text("Valor")
                                },
                                modifier = Modifier.size(width = 210.dp, height = 56.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(Modifier.width(10.dp))
                            OutlinedIconButton(onClick = {
                                avançaMainActivity(context)
                            }) {
                                Icon(Icons.Outlined.Check, contentDescription = null)
                            }
                        }
                    }
                }
            }


        }
    }
}
@Composable
private fun IconColumn(iconColor: Color, nome: String){
    //Estado para controlar a animação
    val animatedAlpha = remember { Animatable(0f) }
    val animatedOffset = remember { Animatable(50f) }

    LaunchedEffect(Unit) {
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        animatedOffset.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
        //Aplica animações de opacidade e deslocamento
            .graphicsLayer {
                alpha = animatedAlpha.value
                translationY = animatedOffset.value
            },
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FaIcon(
            retornaIcon(nome),
            size = 35.dp,
            tint = iconColor)
        Text(nome,
            style = MaterialTheme.typography.titleMedium)
    }
}

private fun retornaIcon(nome: String): FaIconType {
    when(nome) {

        "Aluguel" -> return FaIcons.Home

        "Conta de Água" -> return FaIcons.Water

        "Conta de Energia" -> return FaIcons.Bolt

        "Internet" -> return FaIcons.Globe

        "Supermercado" -> return FaIcons.ShoppingCart

        else -> return FaIcons.Dev
    }

}
private fun avançaMainActivity(context: Context){
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
}

@Composable
fun EditarValorContaPreview(/*navController: NavController*/baseColor: Color){
    Log.d(TAG, "EditarValorConta: $baseColor")
    var text by remember {
        mutableStateOf("")
    }
    Box {
        ColorTransitionFromCenter(baseColor)

    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(text, onValueChange = { value ->
                text = value
            },
                placeholder = {
                    Text("Valor")
                },
                modifier = Modifier.size(width = 110.dp, height = 70.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.width(10.dp))
            OutlinedIconButton(onClick = {}) {
                Icon(Icons.Outlined.Check, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(150.dp))
    }


}
