package com.migueldk17.breeze.ui.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.ui.components.BreezeCard
import com.migueldk17.breeze.ui.components.BreezeTopAppBar

@Composable
fun PaginaInicial(modifier: Modifier = Modifier){
    val scroll = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scroll),) {
        BreezeTopAppBar()
        Spacer(modifier = Modifier.size(15.dp))
        Text("Contas Fixas:",
            fontSize = 14.sp)
        Spacer(modifier = Modifier.size(10.dp))
        BreezeCard(FaIcons.Globe, "Internet")
        BreezeCard(FaIcons.Water, "Conta de Água")
        BreezeCard(FaIcons.Bolt, "Conta de Energia")
        BreezeCard(FaIcons.Home, "Aluguel")
        BreezeCard(FaIcons.ShoppingCart, "Supermercado")
    }


}
@Composable
@Preview(showBackground = true)
private fun Preview(){
    PaginaInicial()
}