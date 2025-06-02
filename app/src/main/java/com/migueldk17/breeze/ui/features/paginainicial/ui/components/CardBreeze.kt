package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.blackPoppinsLightMode
import com.migueldk17.breeze.ui.utils.formataSaldo

import com.migueldk17.breeze.ui.features.paginainicial.viewmodels.PaginaInicialViewModel


//Card de PaginaInicial
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezeCard(
    conta: Conta,
    onClick: () -> Unit,
    apagarConta: () -> Unit,
    apagarParcelas: () -> Unit
){
    Log.d(TAG, "BreezeCard: id no Card ${conta.id}")
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
                }


            }
            //Linha de baixo com o valor monetário da categoria
            Row(
                modifier = Modifier.padding(10.dp)

            ) {
                Spacer(modifier = Modifier.width(68.dp))
                Text(
                    formataSaldo(conta.valor),
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
