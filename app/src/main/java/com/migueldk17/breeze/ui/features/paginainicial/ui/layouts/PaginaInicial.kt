package com.migueldk17.breeze.ui.features.paginainicial.ui.layouts

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieConstants
import com.migueldk17.breeze.R
import com.migueldk17.breeze.MainActivity2
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.AdicionarReceitaBottomSheet
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.BreezeCard
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.features.paginainicial.viewmodels.PaginaInicialViewModel
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.lazy.items
import com.migueldk17.breeze.ui.utils.formataMesAno
import com.migueldk17.breeze.uistate.UiState
import java.time.LocalDate

@Composable
fun PaginaInicial(navController: NavController,
                  viewModel: PaginaInicialViewModel = hiltViewModel()){
    val saldo by viewModel.receita.collectAsStateWithLifecycle()
    val saldoFormatado = saldo
    val contasState by viewModel.contaState.collectAsStateWithLifecycle()

    var showBottomSheet = viewModel.showBottomSheet.collectAsStateWithLifecycle().value

    Column(modifier = Modifier
        .fillMaxWidth()) {
        Spacer(modifier = Modifier.size(20.dp))
        //Card de saldo disponível
        ElevatedCard(modifier = Modifier
            .size(width = 254.dp, height = 49.dp))
        {
             Row(modifier = Modifier.fillMaxSize(),
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
            } }
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
                        val parcelas = viewModel.pegaParcelasDaConta(conta.id).collectAsStateWithLifecycle(emptyList()).value
                        val latestParcela = parcelas.lastOrNull()

                        val filtro = formataMesAno(LocalDate.now()) + "%"

                        val parcelaState = viewModel.observeParcelaDoMes(conta.id, filtro).collectAsStateWithLifecycle(initialValue = UiState.Loading).value

                        val parcelaDoMes = when(parcelaState){
                            is UiState.Loading -> {
                                Log.d(TAG, "PaginaInicial: Parcela sendo carregada")
                                null
                            }
                            is UiState.Empty -> {
                                Log.d(TAG, "PaginaInicial: Nenhuma parcela foi encontrada")
                                null
                            }
                            is UiState.Error -> {
                                val message = parcelaState.exception
                                Log.d(TAG, "PaginaInicial: Um erro foi encontrado: $message")
                                null
                            }
                            is UiState.Success -> {
                                val parcela = parcelaState.data
                                Log.d(TAG, "PaginaInicial: Parcela encontrada: $parcela")
                                parcela

                            }
                        }

                        val isLatestParcela = parcelaDoMes == latestParcela

                        BreezeCard(
                            conta,
                            onClick = {
                                val intent = Intent(navController.context, MainActivity2::class.java)
                                Log.d(TAG, "PaginaInicial: ${conta.id}")
                                intent.putExtra("id", conta.id)
                                navController.context.startActivity(intent)
                            },
                            apagarConta = {  viewModel.apagaConta(conta) },
                            apagarParcelas = { if (parcelas.isNotEmpty()) viewModel.apagaTodasAsParcelas(parcelas) else Log.d(
                                TAG,
                                "PaginaInicial: Não há parcelas disponíveis pra apagar"
                            ) },
                            parcela = parcelaDoMes,
                            isLatestParcela = isLatestParcela

                        )
                    }
                }
            }
        }

    }


    if (showBottomSheet){
        AdicionarReceitaBottomSheet(viewModel)
    }

}

