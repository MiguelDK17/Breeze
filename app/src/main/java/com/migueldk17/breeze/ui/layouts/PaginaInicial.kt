package com.migueldk17.breeze.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.Screen
import com.migueldk17.breeze.ui.components.BreezeCard
import com.migueldk17.breeze.ui.components.BreezeTopAppBar

@Composable
fun PaginaInicial(navController: NavController){

    val internetColor = Color(0xFFD8EBF9)
    val contaAguaColor = Color(0xFFD8EBF9)
    val contaEnergiaColor = Color(0xFFD8EBF9)
    val aluguelColor = Color(0xFFD8EBF9)
    val supermercadoColor = Color(0xFFD8EBF9)
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.size(20.dp))
        ElevatedCard(modifier = Modifier
            .size(width = 254.dp, height = 49.dp))
        {
             Row(modifier = Modifier.fillMaxSize(),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.Center) {
                Text(
                    "Seu Saldo: R$ 3000,00",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = {
                        Log.d(TAG, "ToolbarBreeze: botão clicado")
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
            Color(0xFFD8EBF9),
            Color(0xFF0288D1),
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })

        BreezeCard(FaIcons.Water, "Conta de Água",
            12,
            Color(0xFFE3F2FD),
            Color(0xFF0288D1),
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
        BreezeCard(
            FaIcons.Bolt,
            "Conta de Energia",
            100,
            Color(0xFFFFF9C4),
            Color(0xFFFF9800),
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
        BreezeCard(FaIcons.Home,
            "Aluguel",
            450,
            Color(0xFFDFF2E1),
            Color(0xFF4CAF50),
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
        BreezeCard(FaIcons.ShoppingCart,
            "Supermercado",
            350,
            Color(0xFFE8F5E9),
            Color(0xFFF44336),
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
    }


}