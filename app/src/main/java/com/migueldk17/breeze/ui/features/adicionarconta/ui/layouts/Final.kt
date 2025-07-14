package com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.ui.components.BreezeButton
import com.migueldk17.breeze.ui.components.BreezeOutlinedButton
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.components.PersonalizationCard
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.features.historico.ui.components.retornaValorTotalArredondado
import com.migueldk17.breeze.ui.features.historico.ui.components.DetailsCard
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.avançaMainActivity
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formataTaxaDeJuros


@Composable
fun Final(viewModel: AdicionarContaViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val dadosDaConta = viewModel.dadosContaUI.collectAsStateWithLifecycle().value
    val day = dadosDaConta.data.dayOfMonth
    val month = dadosDaConta.data.monthValue
    val year = dadosDaConta.data.year
    val dataFormatada = "$day/$month/$year"
    val isContaParcelada = viewModel.isContaParcelada.collectAsStateWithLifecycle().value
    Log.d(TAG, "Final: isContaParcelada está assim: $isContaParcelada")
    val porcentagemJuros = viewModel.taxaDeJurosMensal.collectAsStateWithLifecycle().value
    var mostrarDetalhes by remember { mutableStateOf(false) }
    val map = if (isContaParcelada) {
        mapOf(
            "Nome" to dadosDaConta.nome,
            "Categoria" to dadosDaConta.categoria,
            "Sub Categoria" to dadosDaConta.subCategoria,
            "Valor Total" to retornaValorTotalArredondado(
                dadosDaConta.valorParcela,
                dadosDaConta.totalParcelas
            ),
            "Valor da parcela" to formataSaldo(dadosDaConta.valorParcela),
            "Data de pagamento" to dataFormatada,
            "Taxa de juros" to "${formataTaxaDeJuros(porcentagemJuros)} a.m"
        )
    } else {
        mapOf(
            "Nome" to dadosDaConta.nome,
            "Categoria" to dadosDaConta.categoria,
            "Sub Categoria" to dadosDaConta.subCategoria,
            "Valor Total" to formataSaldo(dadosDaConta.valor),
            "Data de pagamento" to dataFormatada
        )
    }


    //Column do Passo Final
    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionText(
            "Sua nova conta está pronta! Quando precisar, ela estará aqui para te ajudar."
        )
        Spacer(modifier = Modifier.size(25.dp))
        //Card já finalizado
        PersonalizationCard(
            nomeConta = dadosDaConta.nome,
            icone = dadosDaConta.icone,
            corIcone = dadosDaConta.corIcone,
            valorMascarado = formataSaldo(dadosDaConta.valor),
            corCard = dadosDaConta.corCard)
        Spacer(modifier = Modifier.size(35.dp))


        BreezeOutlinedButton(
            onClick = { mostrarDetalhes = !mostrarDetalhes },
            text = if (mostrarDetalhes) "Ocultar detalhes" else "Mostrar detalhes"
            )

        AnimatedVisibility(
            visible = mostrarDetalhes,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
        ) {
            DetailsCard(
                mapDeCategoria = map,
                onChangeOpenDialog = { mostrarDetalhes = it },
                isContaParcelada = isContaParcelada,
                isReceita = false
            )
        }
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