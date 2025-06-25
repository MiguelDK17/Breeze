package com.migueldk17.breeze.ui.features.historico.ui.components


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.historico.utils.ShowDetailsCard
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.DetailsCard
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formataTaxaDeJuros
import com.migueldk17.breeze.uistate.UiState
import kotlinx.coroutines.delay

@Composable
fun ContaSecundaria(
    contas: List<Conta>,
    expanded: MutableState<Boolean>
){
    var textoClicado by remember { mutableStateOf(false) }
    var contaMutable by remember { mutableStateOf(Conta(
        id = 0,
        name = "",
        categoria = "",
        subCategoria = "",
        valor = 0.0,
        icon = "",
        colorIcon = 0,
        colorCard = 0,
        dateTime = "",
        isContaParcelada = false
    )) }
    Column {
        if (expanded.value) {
            contas.forEach { conta ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 24.dp)
                            .size(0.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(24.dp)
                    )
                    BreezeIcon(
                        breezeIcon = conta.icon.toBreezeIconsType(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)


                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        conta.name,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                textoClicado = true
                                contaMutable = conta
                            },
                        overflow = TextOverflow.Ellipsis, //Caso o texto seja grande demais coloca ... no final
                        maxLines = 1 //Limita o texto a 1 linha para evitar quebra
                    )

                    Text(
                        formataSaldo(conta.valor),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .clickable {
                                textoClicado = true
                                contaMutable = conta
                            }

                    )

                }
            }
            if (textoClicado){
                 ShowDetailsCard(
                     onChangeTextoClicado = {textoClicado = it},
                     id = contaMutable.id,
                     nameAccount = contaMutable.name,
                     date = contaMutable.dateTime.toLocalDateTime(),
                     valor = contaMutable.valor,
                     category = contaMutable.categoria,
                     subCategory = contaMutable.subCategoria
                 )
            }
        }
    }

}