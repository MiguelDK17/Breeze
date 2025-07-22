package com.migueldk17.breeze.ui.features.historico.ui.components

import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel
import com.migueldk17.breeze.ui.utils.retornaDataFormatadaParaPesquisaNoRoom
import com.migueldk17.breeze.ui.utils.traduzData
import java.time.LocalDate


@Composable
fun Calendario(
    viewModel: HistoricoViewModel){
    val ano = LocalDate.now().year
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = ano.toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Grid com os meses
            GridMes(viewModel, ano)

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}

@Composable
fun GridMes(viewModel: HistoricoViewModel, ano: Int){
    //Lista de meses
    val meses = listOf("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez")

    var mesSelecionado by remember { mutableStateOf<String?>(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 3 colunas para os meses
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(8.dp) // padding para evitar que os meses fiquem colados nas bordas
    ) {
        items(count = meses.size,
            itemContent = {index ->
                MesItem(
                    meses[index],
                    isSelected = meses[index] == mesSelecionado,
                    //Salva o mes clicado na variavel mesSelecionado
                    onClick = {
                        //Formata data pra busca no Room
                        val dataFormatada = retornaDataFormatadaParaPesquisaNoRoom(meses[index], ano)
                        mesSelecionado = meses[index]
                        //Data completa para mostrar na TopAppBar em HistoricoDoMes
                        val dataTraduzida = traduzData(meses[index])
                        //Salva a Data Traduzida
                        viewModel.salvaDataTraduzida(dataTraduzida)
                        //Salva a Data Formatada pra consulta no Room
                        viewModel.salvaDataFormatada(dataFormatada)
                        //Dispara a navegação quando as duas datas estiverem prontas
                        viewModel.disparaNavegarParaTela()
                    })
            })
    }

}

@Composable
fun MesItem(
    mes: String,
    isSelected: Boolean,
    onClick: () -> Unit){

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(64.dp)
            .clip(RoundedCornerShape(12.dp)) //Tamanho de cada item
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f) // Caso for selecionado o card fica na cor mais forte
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp, //Adiciona uma borda quando selecionado
                color = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                //Salva o mes clicado na variavel mesSelecionado
                onClick()
            }
    ){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = mes,
                style = MaterialTheme.typography.bodyMedium, //Estilo de Texto
                color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
               )
           }
    }
}