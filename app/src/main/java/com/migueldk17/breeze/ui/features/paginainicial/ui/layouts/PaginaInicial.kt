package com.migueldk17.breeze.ui.features.paginainicial.ui.layouts

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PaginaInicial(navController: NavController, viewModel: PaginaInicialViewModel = hiltViewModel()){
    val saldo by viewModel.receita.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val saldoFormatado = saldo
    val contas by viewModel.conta.collectAsStateWithLifecycle()
    val carregando by viewModel.carregando.collectAsStateWithLifecycle()

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

        when{
            //Caso o ViewModel passe carregando como true
             carregando -> {
                 LottieAnimation(
                    animationRes = R.raw.loading_breeze,
                    isPlaying = true,
                    iterations = LottieConstants.IterateForever
                )

            }
            //Caso não haja nenhuma conta registrada no Room
            contas.isEmpty() -> {
                ContaNaoEncontrada()
            }

            //Caso nenhuma das condições anteriores forem atendidas é entendido que
            //Há contas registradas no Room
            else -> {
                LazyColumn {
                    items(contas) { conta ->
                        BreezeCard(
                            conta,
                            onClick = {
                                val intent = Intent(navController.context, MainActivity2::class.java)
                                Log.d(TAG, "PaginaInicial: ${conta.id}")
                                intent.putExtra("id", conta.id)
                                navController.context.startActivity(intent)
                            },
                            apagarConta = { viewModel.apagaConta(conta)}
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

