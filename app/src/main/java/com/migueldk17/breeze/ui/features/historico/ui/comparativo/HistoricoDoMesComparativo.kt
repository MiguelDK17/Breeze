package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import android.util.Log
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.GastoCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.SaldoDoMesCard
import com.migueldk17.breeze.ui.theme.RedError
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.MainActivity3
import com.migueldk17.breeze.R
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.domain.model.BreezeDestaques
import com.migueldk17.breeze.domain.model.BreezeInsight
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.BreezeElevatedCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.DestaquesCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
import com.migueldk17.breeze.ui.features.historico.ui.layouts.AnimatedEmptyState
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoComparativoViewModel
import com.migueldk17.breeze.uistate.UiState

@Composable
fun HistoricoDoMesComparativo(
    modifier: Modifier = Modifier,
    viewModel: HistoricoComparativoViewModel = hiltViewModel()
){
    val comparativoModel = viewModel.comparativoModel.collectAsStateWithLifecycle().value
    val listaDeMovimentacoesMensal = comparativoModel.listaDeMovimentacoesMensal
    val listaDeMovimentacoesDiaria = comparativoModel.listaDeMovimentacoesDiaria
    val listaDeMovimentacoesCategoria = comparativoModel.listaDeMovimentacoesCategoria
    val context = LocalContext.current
    val activity = LocalActivity.current

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    val listaSelecionada = when (comparativoModel.tipoDeDados) {
        TipoDeDados.MES -> listaDeMovimentacoesMensal
        TipoDeDados.DIA -> listaDeMovimentacoesDiaria
        TipoDeDados.CATEGORIA -> listaDeMovimentacoesCategoria
    }

    Log.d(TAG, "HistoricoDoMesComparativo:listaSelecionada: $listaSelecionada")
    val mesBackup = viewModel.mes.collectAsStateWithLifecycle().value
    val insight by viewModel.insightDoMes.collectAsStateWithLifecycle()
    val destaques by viewModel.breezeDestaques.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = state,
        transitionSpec = {
            fadeIn(tween(300)) + scaleIn(initialScale = 0.95f) togetherWith fadeOut(tween(200))
        },
        label = "transicao_de_estados"
    ) { state ->
        when (state){
            is UiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }

            }
            is UiState.Empty -> {
                when(comparativoModel.tipoDeDados){
                    TipoDeDados.DIA -> {
                        EmptyStateLayout(
                            image = painterResource(R.drawable.nina_no_calendar),
                            title = "Nenhum gasto hoje!",
                            subTitle = "Dia tranquilo...",
                            voltarPara = {
                                viewModel.voltarParaMes()
                            }
                        )
                    }

                    TipoDeDados.MES -> {
                        AnimatedEmptyState(
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

                    TipoDeDados.CATEGORIA -> {
                        EmptyStateLayout(
                            image = painterResource(R.drawable.empty_state_categoria),
                            title = "Ainda não há categorias para colorir",
                            subTitle = "Adicione movimentações para revelar seu fluxo",
                            voltarPara = {
                                val intent = Intent(context, MainActivity3::class.java)
                                context.startActivity(intent)
                                activity?.finish()
                            }
                        )
                    }
                }
            }
            is UiState.Success -> {
                val data = state.data
                HistoricoDoMesComparativoBody(
                    data = data,
                    comparativoModel = comparativoModel,
                    mesBackup = mesBackup,
                    insight = insight,
                    destaques = destaques,
                    setDia = { viewModel.setDia(it) },
                    setCategoria = { viewModel.setCategoria() },
                    voltarParaMes = { viewModel.voltarParaMes() },
                    modifier = modifier
                )
            }
            is UiState.Error -> {
                val error = state.exception
                Log.d(TAG, "HistoricoDoMesComparativo: erro detectado: $error")
            }
        }
    }

}

@Composable
private fun EmptyStateLayout(
    image: Painter,
    title: String,
    subTitle: String,
    voltarPara: () -> Unit,
    modifier: Modifier = Modifier,

    ){
    val backgroundColor = Color(0xFFD8E6EE)
    val iconSize = 120.dp
    val halfIconSize = iconSize / 2

    val infiniteTransition = rememberInfiniteTransition(label = "breeze_float")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breeze_float_y"
    )

    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = image,
            contentDescription = subTitle,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .offset(y = offsetY.dp),
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFFEBF4F8)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = halfIconSize,
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 24.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BreezeRegularText(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        size = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    DescriptionText(
                        subTitle,
                        color = Color(0xFF798FA0)
                    )
                    Spacer(modifier = Modifier
                        .height(20.dp))

                    BreezeButton(
                        onClick = {
                            voltarPara()
                        },
                        text = "Tudo bem!"
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.calendario_check_removebg_preview),
                contentDescription = "Calendário",
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.TopCenter)
                    .offset(y = -halfIconSize)
            )
        }



    }
}

@Composable
@Preview
private fun Preview(){
    EmptyStateLayout(
        image = painterResource(R.drawable.empty_state_categoria),
        title = "Ainda não há categorias para colorir",
        subTitle = "Adicione movimentações para revelar seu fluxo",
        voltarPara = {}
    )
}

@Composable
private fun HistoricoDoMesComparativoBody(
    data: ComparativoData,
    comparativoModel: ComparativoModel,
    mesBackup: String,
    insight: BreezeInsight?,
    destaques: BreezeDestaques?,
    modifier: Modifier = Modifier,
    setDia: (String) -> Unit = {},
    setCategoria: () -> Unit = {},
    voltarParaMes: () -> Unit = {},
) {
    val scroll = rememberScrollState()


    Column(
        modifier = modifier
            .widthIn(min = 389.dp)
            .heightIn(min = 464.dp)
            .padding(7.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .background(color = RedError)
                .height(10.dp)
        )

        SaldoDoMesCard(
            data = data,
            comparativoModel = comparativoModel,
            mesBackup = mesBackup,
            setCategoria = setCategoria,
            setDia = setDia,
            converteDiaEmMes = voltarParaMes
        )
        Spacer(modifier = Modifier.height(10.dp))

        insight?.let { dica ->
            GastoCard(
                titulo = dica.titulo,
                subTitulo = dica.subTitulo
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        BreezeElevatedCard {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    BreezeIcon(
                        breezeIcon = BreezeIcons.Linear.All.NotificationLinear, //Icone de Destaques
                        contentDescription = null
                    )
                    BreezeRegularText(
                        text = "Destaques",
                        modifier = Modifier
                            .padding(start = 10.dp),
                        size = 16.sp
                    )
                }
                destaques?.let { destaque ->
                    destaque.maiorDespesa.let {
                        DestaquesCard(
                            nomeDaConta = it.descricao.dropLast(4),
                            valor = it.valor,
                            category = it.categoria,
                            icon = it.icon.toBreezeIconsType(),
                            date = it.date,
                            progressBush = Brush.horizontalGradient(
                                listOf(
                                    it.colorIcon,
                                    it.colorIcon.copy(alpha = 0.25f)
                                )
                            ),
                            nomeDoDestaque = "Maior despesa do mês"
                        )
                    }
                    destaque.maiorReceita.let {
                        DestaquesCard(
                            nomeDaConta = it.descricao,
                            valor = it.valor,
                            category = it.categoria,
                            icon = it.icon.toBreezeIconsType(),
                            date = it.date,
                            progressBush = Brush.horizontalGradient(
                                listOf(
                                    it.colorIcon,
                                    it.colorIcon.copy(alpha = 0.25f)
                                )
                            ),
                            nomeDoDestaque = "Maior receita do mês"
                        )
                    }
                }
            }
        }
    }
}