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
        BreezeCard(FaIcons.Globe, "Internet", 150, Color.Gray,
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
        BreezeCard(FaIcons.Water, "Conta de Água", 12, Color.Blue,
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
        BreezeCard(FaIcons.Bolt, "Conta de Energia", 100, Color.Yellow,
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
        BreezeCard(FaIcons.Home, "Aluguel", 450,
             Color.Transparent,
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
        BreezeCard(FaIcons.ShoppingCart, "Supermercado", 350,Color.Red,
            onClick = {
                navController.navigate(Screen.WaveScreen.route)
            })
    }


}