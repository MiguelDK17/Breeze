package com.migueldk17.breeze.ui.features.historico.ui.components


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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.utils.formataSaldo

@Composable
fun ContaSecundaria(contas: List<Conta>, expanded: MutableState<Boolean>){
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
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis, //Caso o texto seja grande demais coloca ... no final
                        maxLines = 1 //Limita o texto a 1 linha para evitar quebra
                    )

                    Text(
                        formataSaldo(conta.valor),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 15.dp)

                    )

                }
            }
        }
    }
}