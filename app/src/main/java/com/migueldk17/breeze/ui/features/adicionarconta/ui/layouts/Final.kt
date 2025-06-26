package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeOutlinedButton
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.avançaMainActivity
import java.util.Locale

@Composable
fun Final(navController: NavController, viewModel: AdicionarContaViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val nomeConta = viewModel.nomeConta.collectAsStateWithLifecycle().value
    val icone = viewModel.iconeCardConta.collectAsStateWithLifecycle().value
    val corIcone = viewModel.corIcone.collectAsStateWithLifecycle().value
    val corCard = viewModel.corCard.collectAsStateWithLifecycle().value
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
        BreezeOutlinedButton(
            onClick = {
                navController.navigate(NavGraph2.Passo1.route)
            },
            text = "Mostrar detalhes"
            )
        Spacer(modifier = Modifier.size(20.dp))

        //Botão que finaliza o ciclo e adiciona a conta ao banco de dados
        BreezeButton(
            onClick = {
            viewModel.salvaContaDatabase()
            avançaMainActivity(context)
        },
            text = "Concluir")
    }
}