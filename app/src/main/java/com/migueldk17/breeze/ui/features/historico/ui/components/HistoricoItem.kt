package com.migueldk17.breeze.ui.features.historico.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.utils.formataSaldo
import java.time.LocalDate
import android.content.ContentValues.TAG
import androidx.compose.foundation.background

import com.migueldk17.breeze.ui.theme.Blue

@Composable
fun HistoricoItem(
    date: LocalDate,
    nameAccountFirst: String,
    breezeIconFirst: BreezeIconsType,
    princeFirst: Double,
    contas: List<Conta>
){
    val expanded = remember{ mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
   )  {
        ContaPrincipal(date, nameAccountFirst, breezeIconFirst, princeFirst)

        if (contas.isNotEmpty()) {
            VerMaisButton(contas, expanded)
        }

        AnimatedVisibility(
            visible = expanded.value,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            ContaSecundaria(contas, expanded)
        }

       }
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    val date = LocalDate.now()
    val nameAccount = "Barbeiro de Fortuna de Minas cujo nome é Julho"
    val breezeIcon = BreezeIcons.Linear.Shop.Bag2
    val price = 25.00
    val listContas = listOf<Conta>()
    HistoricoItem(date = date, nameAccountFirst = nameAccount, breezeIconFirst = breezeIcon, princeFirst = price, contas = listContas)
}
