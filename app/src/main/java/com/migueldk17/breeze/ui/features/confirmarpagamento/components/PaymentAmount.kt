package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeDropdownMenu
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.theme.Blue
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.theme.grayforHint
import com.migueldk17.breeze.ui.theme.grayforTextColorInDropdown

@Composable
fun PaymentAmount(
    isContaParcelada: Boolean
){
     BreezeIcon(
        modifier = Modifier
            .size(40.dp),
        breezeIcon = BreezeIcons.Linear.All.SpotifyLinear,
        contentDescription = "Spotify"
    )
    BreezeRegularText(
        text = "R$ 300,00",
        size = 17.sp,
        fontWeight = FontWeight.SemiBold,
        color = Blue
    )
    DescriptionText(
        text = "Pagamento referente a Spotify",
        color = NavyBlue
    )


}

@Composable
@Preview
private fun Preview(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BreezeRegularText(
            modifier = Modifier.padding(start = 30.dp),
            text = "Juros:")
        BreezeRegularText(
            modifier = Modifier.padding(end = 30.dp),
            text = "0.0% a.m")
    }

}
