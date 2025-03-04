package com.migueldk17.breeze.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.converters.toColor
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.ui.layouts.formataValor
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import java.time.format.DateTimeFormatter


//Card de PaginaInicial
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezeCard(
    conta: Conta,
    viewModel: BreezeViewModel,
    onClick: () -> Unit,
){
    //Variavel que controla o estado do BasicAlertDialog
    val openDialog = remember { mutableStateOf(false) }
    Log.d(TAG, "BreezeCard: ${conta.dateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))}")

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
                Text(conta.name, style = MaterialTheme.typography.bodyLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                Text(formataValor(conta.valor))
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
                                viewModel.apagaConta(conta)
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
