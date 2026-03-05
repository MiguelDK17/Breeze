package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeButtonGroup
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.DestaquesCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.GastoCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.SaldoDoMesCard
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.RedError
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HistoricoDoMesComparativo(
    modifier: Modifier = Modifier
){
    val scroll = rememberScrollState()

    val options = persistentListOf(
        "Dia",
        "Categoria",
        "Mês"
    )
    Column(
        modifier = modifier
            .widthIn(min = 389.dp)
            .heightIn(min = 464.dp)
            .padding(7.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .background(color = RedError)
            .height(10.dp)
        )

        SaldoDoMesCard()

        GastoCard()

        DestaquesCard()


    }
}

@Composable
@Preview
private fun Preview(){
    BreezeTheme() {
        HistoricoDoMesComparativo()
    }
}