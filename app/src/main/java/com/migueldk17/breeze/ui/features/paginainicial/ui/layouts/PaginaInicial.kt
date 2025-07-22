package com.migueldk17.breeze.ui.features.paginainicial.ui.layouts

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieConstants
import com.migueldk17.breeze.R
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.AdicionarReceitaBottomSheet
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.BreezeCard
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.features.paginainicial.viewmodels.PaginaInicialViewModel
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import com.migueldk17.breeze.MainActivity3
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.ui.utils.formataMesAno
import com.migueldk17.breeze.uistate.UiState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaInicial(
    viewModel: PaginaInicialViewModel = hiltViewModel()
){
    val saldo by viewModel.receita.collectAsStateWithLifecycle()

    val saldoFormatado = saldo

    val contasState by viewModel.contaState.collectAsStateWithLifecycle()

    val showBottomSheet = viewModel.showBottomSheet.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val activity = LocalActivity.current

    val hasRequestedAddAccountSheet:Boolean

    if (activity!!.intent.hasExtra("showBottomSheet")){
        hasRequestedAddAccountSheet = activity.intent.getBooleanExtra("showBottomSheet", false)
        viewModel.atualizaBottomSheet(hasRequestedAddAccountSheet)
        activity.intent.removeExtra("showBottomSheet")
    }
    else {
        hasRequestedAddAccountSheet = false
    }

    Log.d(TAG, "PaginaInicial: hasRequestedAddAccountSheet $hasRequestedAddAccountSheet")
    Log.d(TAG, "PaginaInicial: valor da intent: ${activity.intent.hasExtra("showBottomSheet")}")



    Column(modifier = Modifier
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
                //Botão para editar o saldo
                IconButton(
                    onClick = {
                        viewModel.atualizaBottomSheet(true)
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
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text("Suas Contas:",
            fontSize = 14.sp)
        Spacer(modifier = Modifier.size(10.dp))

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
                ContaNaoEncontrada()
            }

            is UiState.Error -> {
                val message = (contasState as UiState.Error).exception
                Log.d(TAG, "PaginaInicial: $message")
            }

            //Caso nenhuma das condições anteriores forem atendidas é entendido que
            //Há contas registradas no Room
            is UiState.Success -> {
                val contas = (contasState as UiState.Success).data
                LazyColumn {
                    items(contas) { conta ->
                        //Pega a lista de parcelas
                        val parcelas = viewModel.pegaParcelasDaConta(conta.id).collectAsStateWithLifecycle(emptyList()).value

                        //Pega a última parcela
                        val latestParcela = parcelas.lastOrNull()

                        //Formata a data para consulta no Room
                        val filtro = formataMesAno(LocalDate.now()) + "%"

                        //Pega as parcelas com o UIState para cobrir os estados da lista
                        val parcelaState = viewModel.observeParcelaDoMes(conta.id, filtro).collectAsStateWithLifecycle(initialValue = UiState.Loading).value

                        //Pega a parcela do mês
                        val parcelaDoMes = when(parcelaState){
                            is UiState.Loading -> {
                                null
                            }
                            is UiState.Empty -> {
                                null
                            }
                            is UiState.Error -> {
                                val message = parcelaState.exception
                                Log.d(TAG, "PaginaInicial: Um erro foi encontrado: $message")
                                null
                            }
                            is UiState.Success -> {
                                val parcela = parcelaState.data
                                parcela

                            }
                        }
                        //Verifica se não há parcelas no mês
                        val semParcelaNoMes = parcelas.isNotEmpty() && parcelaState is UiState.Empty

                        //Pega a data da primeira parcela futura caso não haja parcelas nesse mês, mas haja nos meses subsequentes
                        val dataPrimeiraParcelaFutura = if (semParcelaNoMes) parcelas.first().data.toLocalDate() else null

                        //Verifica se é a última parcela
                        val isLatestParcela = parcelaDoMes == latestParcela


                        BreezeCard(
                            conta,
                            onClick = {
                                val intent = Intent(context, MainActivity3::class.java)
                                intent.putExtra("id", conta.id)
                                context.startActivity(intent)
                            },
                            apagarConta = {  viewModel.apagaConta(conta) },
                            apagarParcelas = { if (parcelas.isNotEmpty()) viewModel.apagaTodasAsParcelas(parcelas) else Log.d(
                                TAG,
                                "PaginaInicial: Não há parcelas disponíveis pra apagar"
                            ) },
                            parcela = parcelaDoMes,
                            isLatestParcela = isLatestParcela,
                            semParcelaNoMes = semParcelaNoMes,
                            dataPrimeiraParcelaFutura = dataPrimeiraParcelaFutura

                        )
                    }
                }
            }
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
