package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import android.util.Log
import android.content.ContentValues.TAG
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
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.entity.Receita
import com.migueldk17.breeze.ui.theme.Blue
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.blackPoppinsLightMode

//Card de PaginaInicial
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezeCardReceita(
    receita: Receita,
    apagarReceita: () -> Unit
){
    //Variavel que controla o estado do BasicAlertDialog
    val openDialog = remember { mutableStateOf(false) }
    val descricao = receita.descricao.ifEmpty { "Receita sem descrição" }
    val valor = receita.valor
    val icon = if (receita.icon.toBreezeIconsType().enum == BreezeIconsEnum.ICON_UNSPECIFIED) {
        BreezeIcons.Linear.Money.DollarCircle
    } else {
        receita.icon.toBreezeIconsType()
    }
    Log.d(TAG, "BreezeCardReceita: argb do icon green ${BreezeIcons.Colors.Vibrant.IconGreen.color}")
    Log.d(TAG, "BreezeCardReceita: argb do icon blue ${BreezeIcons.Colors.Soft.SoftBlue.color}")
    Log.d(TAG, "BreezeCardReceita: ${receita.icon.toBreezeIconsType().enum}")
    Log.d(TAG, "BreezeCardReceita: valor do icon${receita.icon}")

    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BreezeIcons.Colors.Soft.SoftBlue.color)
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
                    breezeIcon = icon,
                    contentDescription = null,
                    color = BreezeIcons.Colors.Vibrant.IconGreen.color,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.size(20.dp))
                Text(descricao,
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
                    // -- ATENÇÃO!!!! BOTÃO MUTADO POR FALTA DE USO. NÃO MEXA AQUI A NÃO SER SE FOR PARA ADICIONAR FUNCIONALIDADE A ELE --
//                    TextButton(onClick = {
//                        onClick()
//                    }) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(Icons.Filled.Add, "Add")
//                            Spacer(modifier = Modifier.size(10.dp))
//                            Text("Editar Valor")
//                        }
//                    }


                }


            }
            //Linha de baixo com o valor monetário da categoria
            Row(
                modifier = Modifier.padding(10.dp)

            ) {
                Spacer(modifier = Modifier.width(68.dp))
                Text(
                    retornaValorNoCard(valor, null),
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
                                apagarReceita()
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