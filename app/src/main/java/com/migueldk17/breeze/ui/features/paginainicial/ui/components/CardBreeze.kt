package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.converters.toColor
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.historico.ui.components.retornaValorTotalArredondado
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.blackPoppinsLightMode
import com.migueldk17.breeze.ui.utils.arredondarValor
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formataValorConta
import java.time.LocalDate

//Card de PaginaInicial
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezeCard(
    conta: Conta,
    onClick: () -> Unit,
    apagarConta: () -> Unit,
    apagarParcelas: () -> Unit,
    parcela: ParcelaEntity?,
    isLatestParcela: Boolean,
    semParcelaNoMes: Boolean,
    dataPrimeiraParcelaFutura: LocalDate?
){


    var isExpanded by remember { mutableStateOf(false) }

    //Variavel que controla o estado do BasicAlertDialog
    val openDialog = remember { mutableStateOf(false) }

    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = conta.colorCard.toColor())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),

        ){
            //Linha de cima(com ícone, texto e etc)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BreezeIcon(
                    breezeIcon = conta.icon.toBreezeIconsType(),
                    contentDescription = null,
                    color = conta.colorIcon.toColor(),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.size(20.dp))
                Text(conta.name,
                    modifier = Modifier
                        .weight(1f),
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (!isSystemInDarkTheme()) blackPoppinsLightMode else DeepSkyBlue,
                    //Caso o texto suma provavelmente deve ter algum elemento com fillMaxWidth na Row/Column
                    overflow = TextOverflow.Ellipsis, //Caso o texto seja grande demais coloca ... no final
                    maxLines = 1 //Limita o texto a 1 linha para evitar quebra

                )
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onClick()
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Add, "Add")
                            Spacer(modifier = Modifier.size(10.dp))
                            Text("Editar Valor")
                        }
                    }

                    //Caso haja parcela no mês OU haja parcela nos meses subsequentes
                    if (parcela != null || semParcelaNoMes) {
                        IconButton(onClick = { isExpanded = !isExpanded }) {
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp
                                else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expandir detalhes"
                            )
                        }
                    }
                }


            }
            //Linha de baixo com o valor monetário da categoria
            Row(
                modifier = Modifier.padding(10.dp)

            ) {
                Spacer(modifier = Modifier.width(68.dp))
                Text(
                    retornaValorNoCard(conta.valor, parcela),
                    color = if (!isSystemInDarkTheme()) blackPoppinsLightMode else DeepSkyBlue
                )
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd){
                    IconButton(onClick = {
                        openDialog.value = true
                    }) {
                        BreezeIcon(
                            breezeIcon = BreezeIcons.Linear.Essetional.PaperBinLinear,
                            contentDescription = "Excluir Conta"
                        )
                    }
                }
            }

            //Caso haja parcela no mês OU haja parcela nos meses subsequentes
            if (parcela != null || semParcelaNoMes) {
                IsExpandableCard(conta, parcela, isLatestParcela, isExpanded, semParcelaNoMes, dataPrimeiraParcelaFutura)
            }

        }
        if (openDialog.value){
            BasicAlertDialog(
                onDismissRequest = {
                    //Dispensa o BasicAlertDialog
                    openDialog.value = false
                }

            ) {
                Surface(
                    modifier = Modifier.size(width = 312.dp, height = 200.dp),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.SpaceAround
                        ) {

                        //Título do BasicAlertDialog
                        Text("Excluir Conta",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 23.sp)

                        //Texto do BasicAlertDialog
                        Text("Você está prestes a excluir esta conta, esta ação será irreversível. Prosseguir ?",
                            style = MaterialTheme.typography.bodyMedium)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = {
                                openDialog.value = false //Botão de cancelar
                            }) {
                                Text("Cancelar")
                            }
                            TextButton(onClick = {
                                apagarConta()
                                apagarParcelas()
                                openDialog.value = false //Botão de confirmar
                            }) {
                                Text("Confirmar")
                            }
                        }
                    }
                }
            }

        }

    }
    Spacer(modifier = Modifier.size(10.dp))
}

@Composable
private fun IsExpandableCard(
    conta: Conta,
    parcela: ParcelaEntity?,
    isLatestParcela: Boolean,
    isExpandable: Boolean,
    semParcelaNoMes: Boolean,
    dataPrimeiraParcelaFutura: LocalDate?
){
     if(isExpandable){
        AnimatedVisibility(visible = conta.isContaParcelada) {
            if (semParcelaNoMes) {
                val dia = dataPrimeiraParcelaFutura!!.dayOfMonth
                val mes = dataPrimeiraParcelaFutura.monthValue
                val ano = dataPrimeiraParcelaFutura.year
                val dataFormatada =  "$dia/$mes/$ano"
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    DescriptionText(
                        "Tá tudo quieto por aqui este mês!",
                        size = 12.9.sp
                    )
                    DescriptionText(
                        "Olha só, a próxima vem em: $dataFormatada",
                        size = 12.9.sp)
                }

            }
            else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val porcentagemJuros = parcela!!.porcentagemJuros

                val data = parcela.data.toLocalDate()
                val dia = data.dayOfMonth
                val mesSeguinte = data.monthValue + 1
                val ano = data.year
                val dataFormatada = if (!isLatestParcela) "$dia/$mesSeguinte/$ano" else "Esta é a última parcela"
                DescriptionText("\uD83D\uDCA1 Conta Parcelada")

                DescriptionText("Parcelas: ${parcela.numeroParcela} de ${parcela.totalParcelas}")
                DescriptionText("Valor da parcela: ${formataSaldo(parcela.valor)}")
                DescriptionText("Juros: $porcentagemJuros %")
                DescriptionText("Próxima parcela: $dataFormatada")

            }

            }
        }
    }
}

private fun retornaValorNoCard(valor: Double,parcela: ParcelaEntity?): String {
    val valor = if (parcela == null) {
        formataValorConta(valor)
    } else {
        retornaValorTotalArredondado(parcela.valor, parcela.totalParcelas)
    }
    return valor

}
