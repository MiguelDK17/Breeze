package com.migueldk17.breeze.ui.features.historico.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formatarNomeConta
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
                       .fillMaxWidth()
                       .background(PastelLightBlue),
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
                           formatarNomeConta(nome = nameAccountFirst, maxChar = nameAccountFirst.length),
                           style = MaterialTheme.typography.bodySmall,
                           fontSize = 15.sp
                       )
                       Column(
                           modifier = Modifier.fillMaxWidth()
                       ) {
                           Text(
                               formataSaldo(princeFirst),
                               style = MaterialTheme.typography.bodySmall,
                               fontSize = 14.sp,
                               modifier = Modifier
                                   .align(Alignment.End)
                                   .padding(horizontal = 15.dp)
                           )

                       }

                   }

               }
        if (contas.isNotEmpty()) {
            TextButton(onClick = { expanded.value = true }) {
                Icon(
                    if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null
                )
                Text(if (expanded.value) "Ver menos" else "Ver mais ${contas.size} contas...")
            }
        }

        if(expanded.value){
            contas.forEach { conta ->
                Row {
                    Spacer(modifier = Modifier
                        .width(45.dp)
                        .background(Color.Cyan))
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .width(60.dp)
                            .background(Color.Black)
                    )
                    BreezeIcon(
                        breezeIcon = conta.icon.toBreezeIconsType(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(20.dp)
                    )
                    Text(
                        conta.name,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        formataSaldo(conta.valor),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp
                    )
                }
            }
        }

       }
}
