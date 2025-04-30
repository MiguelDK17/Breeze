package com.migueldk17.breeze.ui.features.historico.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.utils.formataSaldo
import java.time.LocalDate

@Composable
fun HistoricoItem(
    date: LocalDate,
    nameAccountFirst: String,
    breezeIconFirst: BreezeIconsType,
    princeFirst: Double,
    contas: List<Conta>
){
    val expanded = remember{ mutableStateOf(false) }
    Column(
       horizontalAlignment = Alignment.CenterHorizontally
   )  {
            Row(
                   modifier = Modifier
                       .fillMaxWidth(),
                   verticalAlignment = Alignment.Top,
               ) {
                       Text(
                           "-",
                           modifier = Modifier.padding(horizontal = 15.dp, vertical = 24.dp)
                       )
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier = Modifier.width(60.dp)
                   ) {

                           BoxDate(date)
                   }
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(71.dp),
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       BreezeIcon(breezeIcon = breezeIconFirst,
                           contentDescription = null,
                           modifier = Modifier
                               .padding(horizontal = 15.dp)
                               .size(25.dp))
                       Text(
                           nameAccountFirst,
                           style = MaterialTheme.typography.bodySmall,
                           fontSize = 15.sp,
                           modifier = Modifier
                               .weight(1f)
                               .padding(end = 8.dp),
                           overflow = TextOverflow.Ellipsis,
                           maxLines = 1

                       )

                           Text(
                               formataSaldo(princeFirst),
                               style = MaterialTheme.typography.bodySmall,
                               fontSize = 14.sp,
                               modifier = Modifier
                                   .padding(horizontal = 15.dp),
                               textAlign = TextAlign.End
                           )



                   }

               }

        if (contas.isNotEmpty()) {
            TextButton(onClick = { expanded.value = !expanded.value }) {
                Icon(
                    if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
                Text(if (expanded.value) "Ver menos" else "Ver mais ${contas.size} contas...")
            }
        }

        AnimatedVisibility(
            visible = expanded.value,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                if (expanded.value) {
                    contas.forEach { conta ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
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
                                    .padding(horizontal = 20.dp)

                            )
                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                conta.name,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
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



       }
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    val date = LocalDate.now()
    val nameAccount = "Barbeiro de Fortuna de Minas cujo nome é Julho"
    val breezeIcon = BreezeIcons.Linear.Shop.Bag2
    val price = 25.00
    val listContas = listOf<Conta>()
    HistoricoItem(date = date, nameAccountFirst = nameAccount, breezeIconFirst = breezeIcon, princeFirst = price, contas = listContas)
}
