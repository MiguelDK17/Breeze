package com.migueldk17.breeze.ui.layouts

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.MainActivity2
import com.migueldk17.breeze.ui.components.BreezeCard
import com.migueldk17.breeze.ui.theme.cardAguaColor
import com.migueldk17.breeze.ui.theme.cardAluguelColor
import com.migueldk17.breeze.ui.theme.cardEnergiaColor
import com.migueldk17.breeze.ui.theme.cardInternetColor
import com.migueldk17.breeze.ui.theme.cardSupermercado
import com.migueldk17.breeze.ui.theme.iconAluguelColor
import com.migueldk17.breeze.ui.theme.iconContaAguaColor
import com.migueldk17.breeze.ui.theme.iconContaEnergiaColor
import com.migueldk17.breeze.ui.theme.iconInternetColor
import com.migueldk17.breeze.ui.theme.iconSupermercadoColor
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaInicial(navController: NavController, viewModel: BreezeViewModel = hiltViewModel()){
    val saldo by viewModel.saldo.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.size(20.dp))
        //Card de saldo disponível
        ElevatedCard(modifier = Modifier
            .size(width = 254.dp, height = 49.dp))
        {
             Row(modifier = Modifier.fillMaxSize(),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.Center) {
                Text(
                    "Seu Saldo: R$ ${saldo?.valor ?: "Carregando..."}",
                    style = MaterialTheme.typography.titleMedium
                )
                 //Botão para editar o saldo
                IconButton(
                    onClick = {
                        scope.launch { sheetState.show() }
                    },
                    modifier = Modifier
                        .size(23.dp)
                        .padding(0.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        "",
                        modifier = Modifier.size(30.dp)
                    )
                }
            } }
        Spacer(modifier = Modifier.size(15.dp))
        Text("Contas Fixas:",
            fontSize = 14.sp)
        Spacer(modifier = Modifier.size(10.dp))

        BreezeCard(FaIcons.Globe,
            "Internet",
            150,
            cardInternetColor, //Cor do card
            iconInternetColor,  //Cor do ícone
            onClick = {
                onClick(navController, cardInternetColor, iconInternetColor, "Internet")
            })

        BreezeCard(FaIcons.Water,
            "Conta de Água",
            12,
            cardAguaColor, //Cor do card
            iconContaAguaColor,  //Cor do ícone
            onClick = {
                onClick(navController, cardAguaColor, iconContaAguaColor, "Conta de Água")
            })
        BreezeCard(
            FaIcons.Bolt,
            "Conta de Energia",
            100,
            cardEnergiaColor, //Cor do card
            iconContaEnergiaColor, //Cor do ícone
            onClick = {
                onClick(navController, cardEnergiaColor, iconContaEnergiaColor, "Conta de Energia")
            })
        BreezeCard(FaIcons.Home,
            "Aluguel",
            450,
            cardAluguelColor, //Cor do card
            iconAluguelColor,  //Cor do ícone
            onClick = {
                onClick(navController, cardAluguelColor, iconAluguelColor, "Aluguel")
            })
        BreezeCard(FaIcons.ShoppingCart,
            "Supermercado",
            350,
            cardSupermercado, //Cor do card
            iconSupermercadoColor, //Cor do ícone
            onClick = {
                onClick(navController, cardSupermercado, iconSupermercadoColor, "Supermercado")
            })
    }
}
private fun onClick(navController: NavController, cardColor: Color, iconColor: Color, nomeConta: String){

    val intent = Intent(navController.context, MainActivity2::class.java)
    //Transforma a cor em Argb para passar para a intent
    val transformaCorIcon = iconColor.toArgb()

    //Transforma a cor em Argb para passar para a intent
    val transformaCorCard = cardColor.toArgb()

    //Manda as cores do card e do icone para um array
    val arrayList = intArrayOf(transformaCorCard, transformaCorIcon)


    intent.putExtra("color", arrayList)
    intent.putExtra("nome", nomeConta)
    navController.context.startActivity(intent)
}