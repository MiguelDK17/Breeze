package com.migueldk17.breeze.ui.features.paginainicial.ui.layouts

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieConstants
import com.migueldk17.breeze.R
import com.migueldk17.breeze.MainActivity2
import com.migueldk17.breeze.MoneyVisualTransformation
import com.migueldk17.breeze.ui.animation.LottieAnimation
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.BreezeCard
import com.migueldk17.breeze.ui.formataSaldo
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale")
@Composable
fun PaginaInicial(navController: NavController, viewModel: BreezeViewModel = hiltViewModel()){
    val saldo by viewModel.saldo.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val saldoFormatado = saldo?.valor
    val contas by viewModel.conta.collectAsStateWithLifecycle()
    val carregando by viewModel.carregando.collectAsStateWithLifecycle()
    



    //Estados para controlar o ModalBottomSheet
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }


    //Estado para armazenar o saldo
    var saldoInput by remember { mutableStateOf("") }
    var isSaldoCorrectly by remember { mutableStateOf(false) }



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
                        showBottomSheet = true
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
        Text("Contas Fixas:",
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
                        BreezeCard(conta, viewModel) {
                            val intent = Intent(navController.context, MainActivity2::class.java)
                            intent.putExtra("id", conta.id)
                            navController.context.startActivity(intent)
                        }
                    }
                }
            }
        }

    }


    if (showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Editar Saldo", style = MaterialTheme.typography.titleMedium)
                //AQUI FICARÁ O OUTLINEDTEXTFIELD
                OutlinedTextField(
                    value = saldoInput,
                    onValueChange = { value ->
                        saldoInput = value.filter { it.isLetterOrDigit() }
                        Log.d(TAG, "PaginaInicial: $saldoInput")
                    },
                    label = { Text("Novo Saldo") },
                    //Aceita apenas números
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    //Apenas uma linha
                    singleLine = true,
                    //Verificação de erro
                    isError = saldoInput.isNotEmpty() && (saldoInput.toIntOrNull() ?: 0) !in 1000..9999999,
                    visualTransformation = MoneyVisualTransformation()
                )
                if (saldoInput.isNotEmpty() && (saldoInput.toIntOrNull() ?: 0) !in 1000..9999999) {
                    isSaldoCorrectly = false
                    Text(
                        text = "O saldo deve estar entre R$:10,00 e R$:99.999,00",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                else {
                    isSaldoCorrectly = true
                }
                Button(onClick = {
                    viewModel.atualizaSaldo(saldoInput.toDouble()) //Atualiza o saldo
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible){
                            showBottomSheet = false  //Fecha o BottomSheet
                        }
                    }
                },
                    enabled = isSaldoCorrectly && saldoInput != ""
                ) {
                    Text("Salvar")
                }


            }
        }
    }

}

