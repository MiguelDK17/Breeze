package com.migueldk17.breeze.ui.features.historico.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.DetailsCard
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formataValorConta
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun ContaPrincipal(
    date: LocalDate, //Data de criação da conta
    nameAccount: String, //Nome da conta
    breezeIcon: BreezeIconsType, //Icone BreezeIcon
    price: Double, //Valor da conta
    id: Long

){
    var textoClicado by remember {mutableStateOf(false)}
    val map = mapOf(
        "Nome:" to nameAccount.toString(),
        "Valor Total:" to formataValorConta(price),
        "Valor da parcela:" to "",
        "Data de pagamento:" to date.toString(),
        "Taxa de juros:" to ""
    )
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

            BoxDate(date) //Box de data
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(71.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BreezeIcon(breezeIcon = breezeIcon,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .size(25.dp))
            Text(
                nameAccount,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 15.sp,
                modifier = Modifier
                    .weight(1f) //Adiciona peso ao Text
                    .padding(end = 8.dp)
                    .clickable{
                        textoClicado = true
                    }
                    ,
                overflow = TextOverflow.Ellipsis, //Caso o texto seja grande demais coloca ... no final
                maxLines = 1 //Limita o texto a 1 linha para evitar quebra

            )

            Text(
                formataSaldo(price),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 15.dp),
                textAlign = TextAlign.End
            )

        }
    }

    if (textoClicado){
        DetailsCard(mapDeCategoria = map, onChangeOpenDialog = {textoClicado = it}, id)
    }
}