package com.migueldk17.breeze.ui.features.paginainicial.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieConstants
import com.migueldk17.breeze.R
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.AdicionarReceitaBottomSheet
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.BreezeCardConta
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.features.paginainicial.viewmodels.PaginaInicialViewModel
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.converters.toStatus
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.MovimentacaoEntity
import com.migueldk17.breeze.enums.StatusConta
import com.migueldk17.breeze.ui.components.BreezeButtonGroup
import com.migueldk17.breeze.ui.features.historico.ui.components.DetailsCard
import com.migueldk17.breeze.ui.features.historico.ui.components.retornaValorTotalArredondado
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.BreezeCardReceita
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.DialogExcluirConta
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.SwipeableBreezeCardConta
import com.migueldk17.breeze.ui.utils.formataMesAno
import com.migueldk17.breeze.ui.utils.formataTaxaDeJuros
import com.migueldk17.breeze.uistate.UiState
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PaginaInicial(
    modifier: Modifier = Modifier,
    viewModel: PaginaInicialViewModel = hiltViewModel()
){
    val saldo by viewModel.receita.collectAsStateWithLifecycle()

    val saldoFormatado = saldo

    val contasState by viewModel.contaState.collectAsStateWithLifecycle()

    val receitaState by viewModel.movimentacaoEntityState.collectAsStateWithLifecycle()

    val showBottomSheet = viewModel.showBottomSheet.collectAsStateWithLifecycle().value

    val activity = LocalActivity.current

    var hasRequestedAddAccountSheet: Boolean

    if (activity!!.intent.hasExtra("showBottomSheet")){
        hasRequestedAddAccountSheet = activity.intent.getBooleanExtra("showBottomSheet", false)
        viewModel.atualizaBottomSheet(hasRequestedAddAccountSheet)
        activity.intent.removeExtra("showBottomSheet")
    }

    val optionsLinear = mapOf(
        BreezeIcons.Linear.Money.MoneySend to "Contas",
        BreezeIcons.Linear.Money.MoneyRecive to "Receitas"
    )
    val optionsOutlined = mapOf(
        BreezeIcons.Outlined.Money.MoneySend to "Contas",
        BreezeIcons.Outlined.Money.MoneyRecive to "Receitas"
    )
    var selectedIndexButtonGroup by remember{ mutableIntStateOf(0) }





    Column(modifier = modifier
        .fillMaxWidth()) {
        Spacer(modifier = Modifier.size(20.dp))
        //Card de saldo disponível
        ElevatedCard(
            modifier = Modifier
                .widthIn(min = 254.dp)
                .heightIn(min = 49.dp, max = 100.dp)
        )
        {
            Row(modifier = Modifier
                .widthIn(min = 254.dp)
                .heightIn(min = 49.dp, max = 100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Text(
                    "Seu Saldo: ${formataSaldo(saldoFormatado)}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(5.dp))
                //Botão para editar o saldo
                IconButton(
                    onClick = {
                        viewModel.atualizaBottomSheet(true)
                    },
                    modifier = Modifier
                        .size(23.dp)
                ) {
                    Icon(
                        Icons.Default.AddCircleOutline,
                        "",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        BreezeButtonGroup(
            options = optionsLinear.values.toImmutableList(),
            unCheckedIcons = optionsOutlined.keys.toImmutableList(),
            checkedIcons = optionsLinear.keys.toImmutableList(),
            onChangeSelectedIndex = {
                selectedIndexButtonGroup = it
            }
        )
        Log.d(TAG, "PaginaInicial:valor do botão $selectedIndexButtonGroup")
        Spacer(modifier = Modifier.size(10.dp))

        if (selectedIndexButtonGroup == 0){
            LazyColumnContas(contasState, viewModel)
        }
        else {
            LazyColumnReceitas(receitaState, viewModel)
        }
    }

    if (showBottomSheet){
        AdicionarReceitaBottomSheet(
            atualizaBottomSheet = {viewModel.atualizaBottomSheet(it)},
            adicionaReceita = { saldo, descricao, data, icon ->
                viewModel.adicionaReceita(saldo, descricao, data, icon)
            }
        )
    }
}

@Composable
private fun LazyColumnContas(contasState: UiState<List<Conta>>, viewModel: PaginaInicialViewModel){
    val context = LocalContext.current

    when(contasState){
        //Caso o ViewModel passe carregando como true
        is UiState.Loading -> {
            LottieAnimation(
                animationRes = R.raw.loading_breeze,
                isPlaying = true,
                iterations = LottieConstants.IterateForever
            )
        }
        //Caso não haja nenhuma conta registrada no Room
        is UiState.Empty -> {
            ContaNaoEncontrada(categoria = "conta")
        }

        is UiState.Error -> {
            val message = contasState.exception
            Log.d(TAG, "PaginaInicial: $message")
        }

        //Caso nenhuma das condições anteriores forem atendidas é entendido que
        //Há contas registradas no Room
        is UiState.Success -> {
            val contas = contasState.data
            LazyColumn {
                items(
                    items = contas,
                    key = { it.id }) { conta ->
                    //Pega a lista de parcelas
                    val parcelas = viewModel.pegaParcelasDaConta(conta.id).collectAsStateWithLifecycle(emptyList()).value

                    val haveInstallment = parcelas.isNotEmpty()
                    val nome = conta.name
                    val categoria = conta.categoria
                    val subCategoria = conta.subCategoria
                    val date = conta.dateTime.toLocalDateTime()
                    val valorDaConta = conta.valor

                    val day = date.dayOfMonth
                    val month = date.monthValue
                    val year = date.year
                    val dataFormatada = "$day/$month/$year"
                    val parcelaTeste = parcelas.firstOrNull()
                    Log.d(TAG, "LazyColumnContas: $parcelaTeste")

                    val immutableMap = if (parcelaTeste != null) {
                        persistentMapOf(
                            "Nome" to nome,
                            "Categoria" to categoria,
                            "Sub Categoria" to subCategoria,
                            "Valor Total" to retornaValorTotalArredondado(
                                valorParcela = parcelaTeste.valor,
                                totalParcelas = parcelaTeste.totalParcelas
                            ),
                            "Valor da parcela" to formataSaldo(parcelaTeste.valor),
                            "Data de criação" to dataFormatada,
                            "Taxa de juros" to "${formataTaxaDeJuros(parcelaTeste.porcentagemJuros)} a.m"
                        )
                    } else {
                        persistentMapOf(
                            "Nome" to nome,
                            "Categoria" to categoria,
                            "Sub Categoria" to subCategoria,
                            "Valor Total" to formataSaldo(valorDaConta),
                            "Data de criação" to dataFormatada
                        )
                    }

                    var showDialogDetalhes by remember(conta.id) {
                        mutableStateOf(false)
                    }
                    var showDialogExcluir by remember(conta.id) {
                        mutableStateOf(false)
                    }
                    val statusConta = conta.status.toStatus()

                    Log.d(TAG, "LazyColumnContas: $statusConta")

                    val modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.5.dp)
                        .border(width = 0.5.dp, color = devolveColor(statusConta), shape = RoundedCornerShape(12.5.dp))



                    SwipeableBreezeCardConta(
                        onDetalhes = { value ->
                            showDialogDetalhes = value
                        },
                        onExcluir = { value ->
                            showDialogExcluir = value
                        },
                        modifier = modifier
                    ) {
                        BreezeCardConta(
                            conta,
                            parcelas.toImmutableList(),
                            haveInstallment = haveInstallment,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (showDialogDetalhes) {
                            DetailsCard(
                                mapDeCategoria = immutableMap,
                                onChangeOpenDialog = {
                                    showDialogDetalhes = false
                                },
                                isContaParcelada = haveInstallment,
                                isReceita = false
                            )
                        }
                        if (showDialogExcluir) {
                            DialogExcluirConta(
                                openDialogExcluirConta = {
                                    showDialogExcluir = it
                                },
                                apagarConta = {  viewModel.apagaConta(conta) },
                                apagarParcelas = { if (parcelas.isNotEmpty()) viewModel.apagaTodasAsParcelas(parcelas) else Log.d(
                                    TAG,
                                    "PaginaInicial: Não há parcelas disponíveis pra apagar"
                                ) },
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun LazyColumnReceitas(movimentacaoEntityState: UiState<List<MovimentacaoEntity>>, viewModel: PaginaInicialViewModel){
    when(movimentacaoEntityState){
        //Caso o ViewModel passe carregando como true
        is UiState.Loading -> {
            LottieAnimation(
                animationRes = R.raw.loading_breeze,
                isPlaying = true,
                iterations = LottieConstants.IterateForever
            )
        }
        //Caso não haja nenhuma conta registrada no Room
        is UiState.Empty -> {
            ContaNaoEncontrada("receita")
        }

        is UiState.Error -> {
            val message = movimentacaoEntityState.exception
            Log.d(TAG, "PaginaInicial: $message")
        }

        //Caso nenhuma das condições anteriores forem atendidas é entendido que
        //Há contas registradas no Room
        is UiState.Success -> {
            val contas = movimentacaoEntityState.data
            LazyColumn {
                items(contas) { receita ->
                    BreezeCardReceita(
                        receita,
                        apagarReceita = {  viewModel.apagaReceita(receita) },
                    )
                }
            }
        }
    }
}

private fun devolveColor(statusConta: StatusConta): Color {
    val color = when(statusConta){
        StatusConta.ATRASADA -> Color.Red
        else -> {
            Color.Unspecified
        }
    }
    return color
}
