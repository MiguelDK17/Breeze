package com.migueldk17.breeze.ui.features.historico.ui.layouts


import android.content.Intent
import androidx.activity.compose.LocalActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.MainActivity
import com.migueldk17.breeze.MainActivity2
import com.migueldk17.breeze.MainActivity3
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.features.historico.ui.components.HistoricoItem
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoDoMesConta(
    modifier: Modifier,
    viewModelContas: HistoricoDoMesViewModel,
    ) {
    val contas = viewModelContas.contasPorMes.collectAsStateWithLifecycle().value

    //Pega as contas já filtradas por data da mais recente a mais antiga
    val historico = viewModelContas.historico.collectAsStateWithLifecycle().value

    val dataFormatada = viewModelContas.data.collectAsStateWithLifecycle().value

    val listaVazia = viewModelContas.listaVazia.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        if (dataFormatada.isNotEmpty()) {
            viewModelContas.observarContaPorMes()
        }
    }

    if (listaVazia){
        ListaVaziaHistorico(
            animationRes = R.raw.empty_ghost,
            titleText = "Nenhuma conta por aqui... 👻",
            descriptionText1 = "Parece que suas contas ainda estão no mundo dos fantasmas.",
            descriptionText2 = "Crie uma pra começar a organizar tudo certinho!",
            buttonText = "Criar Conta",
            onClick = {
                val intent = Intent(context, MainActivity3::class.java)
                context.startActivity(intent)
                activity?.finish()
            }
        )
    }
    else {

    Column(
        modifier = modifier
    ) {
        val modifier = Modifier.size(width = 360.dp, height = 295.dp)

        GraficoDeBarras(contas, modifier)

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
            LazyColumnContas(historico)

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
}

@Composable
private fun LazyColumnContas(historicoContas: List<HistoricoDoDia>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

    ) {
        items(historicoContas) { dia ->
            //Paga o tamanho da lista
            val size = historicoContas.indexOf(dia)
            //Verifica se a conta é a última
            val isLastItem = size == historicoContas.lastIndex

            val linhaDoTempoPrincipal =
                LinhaDoTempoModel(
                    name = dia.primaryTimeline.name,
                    icon = dia.primaryTimeline.icon,
                    valor = dia.primaryTimeline.valor,
                    id = dia.primaryTimeline.id,
                    dateTime = dia.primaryTimeline.dateTime,
                    category = dia.primaryTimeline.category,
                    subCategory = dia.primaryTimeline.subCategory
                )

            HistoricoItem(
                linhaDoTempoPrincipal = linhaDoTempoPrincipal,
                lastIndex = isLastItem,
                linhaDoTempoOther = dia.otherTimeline
            )

        }

    }
}


