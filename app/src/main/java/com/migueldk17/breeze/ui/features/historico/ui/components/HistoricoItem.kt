package com.migueldk17.breeze.ui.features.historico.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formatarNomeConta
import java.time.LocalDateTime

@Composable
fun HistoricoItem(
    date: LocalDateTime,
    breezeIcon: BreezeIconsType,
    nameAccount: String,
    price: Double
){
   Column(
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Row(
           modifier = Modifier
               .height(71.dp)
               .fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically
       ) {
           Text("-",
               modifier = Modifier.padding(horizontal = 15.dp))
           BoxDate(date)
           BreezeIcon(breezeIcon = breezeIcon,
               contentDescription = null,
               modifier = Modifier
                   .padding(horizontal = 15.dp)
                   .size(25.dp))
           Text(
               formatarNomeConta(nome = nameAccount),
               style = MaterialTheme.typography.bodySmall,
               fontSize = 15.sp,
               modifier = Modifier.background(PastelLightBlue))
           Column(
               modifier = Modifier.fillMaxWidth()
           ) {
               Text(
                   formataSaldo(price),
                   style = MaterialTheme.typography.bodySmall,
                   fontSize = 14.sp,
                   modifier = Modifier
                       .align(Alignment.End)
                       .padding(horizontal = 15.dp)
                       .background(PastelLightBlue),
               )
           }
       }
       /*TextButton(onClick = {}) {
           Icon(Icons.Filled.ArrowDropDown,
               contentDescription = null)
           Text("Ver mais 2 contas...")
       }*/
   }
}
