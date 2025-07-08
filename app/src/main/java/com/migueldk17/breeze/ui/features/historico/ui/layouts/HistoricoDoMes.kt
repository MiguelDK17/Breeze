package com.migueldk17.breeze.ui.features.historico.ui.layouts


import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.converters.toGraficoDoDia
import com.migueldk17.breeze.converters.toGraficoDoDiaList
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDiaContas
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.features.historico.ui.components.HistoricoItem
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoReceitaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoDoMes(
    modifier: Modifier,
    viewModelContas: HistoricoDoMesViewModel,
    viewModelReceita: HistoricoReceitaViewModel,
    tipoDeLista: String

    ) {
    val contas = viewModelContas.contasPorMes.collectAsStateWithLifecycle().value

    val receita = viewModelReceita.receitasPorMes.collectAsStateWithLifecycle().value

    //Pega as contas já filtradas por data da mais recente a mais antiga
    val historico = viewModelContas.historico.collectAsStateWithLifecycle().value

    val dataFormatada = viewModelContas.data.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        Log.d(TAG, "HistoricoDoMes: $dataFormatada")
        if (dataFormatada.isNotEmpty()) {
            viewModelContas.observarContaPorMes()
            viewModelReceita.observarReceitasPorMes()
        }

    }

    Column(
        modifier = modifier
    ) {
        val modifier = Modifier.size(width = 360.dp, height = 295.dp)
        if (tipoDeLista == "Contas") {
            GraficoDeBarras(contas.toGraficoDoDia(), modifier)
        }
        else {
            GraficoDeBarras(receita.toGraficoDoDiaList(), modifier)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                "Linha do Tempo",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            //Fade do final da lista
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            )

        }
    }
    }

@Composable
private fun LazyColumnContas(historicoContas: List<HistoricoDoDiaContas>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

    ) {
        items(historicoContas) { dia ->
            //Paga o tamanho da lista
            val size = historicoContas.indexOf(dia)
            //Verifica se a conta é a última
            val isLastItem = size == historicoContas.lastIndex



            HistoricoItem(
                date = dia.data,
                nameAccountFirst = dia.contaPrincipal.name,
                breezeIconFirst = dia.contaPrincipal.icon.toBreezeIconsType(),
                princeFirst = dia.contaPrincipal.valor,
                lastIndex = isLastItem,
                linhaDoTempoModel = dia.outrasContas,
                idContaPrincipal = dia.contaPrincipal.id,
                categoryPrincipal = dia.contaPrincipal.categoria,
                subCategoryPrincipal = dia.contaPrincipal.subCategoria,
                isContaParceladaContaPrincipal = dia.contaPrincipal.isContaParcelada
            )

        }
    }
}
@Composable
private fun LazyColumnReceitas(viewModelReceita: HistoricoReceitaViewModel){
    val historicoReceitas = viewModelReceita.receitasOrganizadas.collectAsStateWithLifecycle().value

    LazyColumn {
        items(historicoReceitas) { dia ->
            val size = historicoReceitas.indexOf(dia)
            val isLastItem = size == historicoReceitas.lastIndex



        }
    }
}

