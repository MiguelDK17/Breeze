package com.migueldk17.breeze.ui.features.historico.ui.components

import android.util.Log
import android.content.ContentValues.TAG
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.utils.ShowDetailsCard
import com.migueldk17.breeze.ui.utils.arredondarValor
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formataValorConta

@Composable
fun ContaPrincipal(
    linhaDoTempoModel: LinhaDoTempoModel,

){
    val nameAccount = linhaDoTempoModel.name
    val breezeIcon = linhaDoTempoModel.icon.toBreezeIconsType()
    val price = linhaDoTempoModel.valor
    var textoClicado by remember {mutableStateOf(false)}
    val trulyBreezeIcon =  if(breezeIcon.enum.name == "ICON_UNSPECIFIED") BreezeIcons.Linear.Money.DollarCircle else breezeIcon

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
            BoxDate(linhaDoTempoModel.dateTime.toLocalDate()) //Box de data
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(71.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BreezeIcon(
                breezeIcon = trulyBreezeIcon,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .size(25.dp)
            )
            Text(
                nameAccount,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 15.sp,
                modifier = Modifier
                    .weight(1f) //Adiciona peso ao Text
                    .padding(end = 8.dp)
                    .clickable {
                        textoClicado = true
                    },
                overflow = TextOverflow.Ellipsis, //Caso o texto seja grande demais coloca ... no final
                maxLines = 1 //Limita o texto a 1 linha para evitar quebra

            )

            Text(
                formataSaldo(price),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .clickable {
                        textoClicado = true
                    },
                textAlign = TextAlign.End
            )

        }
        if (textoClicado){
            ShowDetailsCard(
                linhaDoTempoModel = linhaDoTempoModel,
                onChangeTextoClicado = {textoClicado = it},
            )
        }
    }

}

internal fun retornaValorTotalArredondado(valorParcela: Double, totalParcelas: Int): String {
    val valorTotal = valorParcela * totalParcelas
    val totalArredondado = arredondarValor(valorTotal)
    val totalFormatado = formataValorConta(totalArredondado)
    return totalFormatado
}