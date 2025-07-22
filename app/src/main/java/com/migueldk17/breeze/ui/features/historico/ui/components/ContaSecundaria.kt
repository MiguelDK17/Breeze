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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.utils.ShowDetailsCard
import com.migueldk17.breeze.ui.utils.formataSaldo
import java.time.LocalDateTime

@Composable
fun ContaSecundaria(
    linhaDoTempoModel: List<LinhaDoTempoModel>,
    expanded: MutableState<Boolean>
){

    var textoClicado by remember { mutableStateOf(false) }
    var timeLineMutable by remember { mutableStateOf(LinhaDoTempoModel(
        id = 0,
        name = "",
        category = "",
        subCategory = "",
        valor = 0.0,
        icon = "",
        colorIcon = 0,
        colorCard = 0,
        dateTime = LocalDateTime.now(),
        isContaParcelada = false
    )) }
    Column {
        if (expanded.value) {
            linhaDoTempoModel.forEach { tempoModel ->
                val name = tempoModel.name.ifEmpty { "Receitas" }
                tempoModel.name = name
                val trulyBreezeIcon =  if(tempoModel.icon.toBreezeIconsType().enum.name == "ICON_UNSPECIFIED") BreezeIcons.Linear.Money.DollarCircle else tempoModel.icon.toBreezeIconsType()
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
                        breezeIcon = trulyBreezeIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        name,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                textoClicado = true
                                timeLineMutable = tempoModel
                            },
                        overflow = TextOverflow.Ellipsis, //Caso o texto seja grande demais coloca ... no final
                        maxLines = 1 //Limita o texto a 1 linha para evitar quebra
                    )

                    Text(
                        formataSaldo(tempoModel.valor),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .clickable {
                                textoClicado = true
                                timeLineMutable = tempoModel
                            }
                    )
                }
            }
            if (textoClicado){
                Log.d(TAG, "ContaSecundaria: valor da conta: ${timeLineMutable.valor}")
                 ShowDetailsCard(
                     linhaDoTempoModel = timeLineMutable,
                     onChangeTextoClicado = {textoClicado = it},
                 )
            }
        }
    }

}