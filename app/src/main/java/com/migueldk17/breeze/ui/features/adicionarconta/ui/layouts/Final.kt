package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.avançaMainActivity
import java.util.Locale

@Composable
fun Final(navController: NavController, viewModel: AdicionarContaViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val nomeConta = viewModel.nomeConta.collectAsState().value
    val icone = viewModel.iconeCardConta.collectAsState().value
    val corIcone = viewModel.corIcone.collectAsState().value
    val corCard = viewModel.corCard.collectAsState().value

    //valor da conta armazenado no viewModel
    val valorConta = viewModel.valorConta.collectAsState().value
    //Pega o valor da conta do viewModel e formata para valores monetários
    val valorMascarado = String.format(Locale.getDefault(),"R$: %.2f", valorConta)

    //Column do Passo Final
    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionText("Sua nova conta está pronta! Quando precisar, ela estará aqui para te ajudar.",)
        Spacer(modifier = Modifier.size(25.dp))
        //Card já finalizado
        PersonalizationCard(nomeConta = nomeConta, icone = icone, corIcone = corIcone, valorMascarado = valorMascarado, corCard = corCard)
        Spacer(modifier = Modifier.size(35.dp))

        //Botão para voltar ao Passo1 para adicionar uma nova conta
        OutlinedButton(
            onClick = {
                navController.navigate(NavGraph2.Passo1.route)
            }
            ) {
                Text("Adicionar nova conta")
            }
        Spacer(modifier = Modifier.size(20.dp))

        //Botão que finaliza o ciclo e adiciona a conta ao banco de dados
            Button(onClick = {
                try {
                    viewModel.salvaContaDatabase()
                } catch (e: IllegalStateException){
                    Log.d(TAG, "Final: $e")
                }
                avançaMainActivity(context)
            }
            ) {
                Text("Concluir")
            }
    }
}