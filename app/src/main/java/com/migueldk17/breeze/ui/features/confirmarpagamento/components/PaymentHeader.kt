package com.migueldk17.breeze.ui.features.confirmarpagamento.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.confirmarpagamento.state.ConfirmPaymentState
import com.migueldk17.breeze.ui.theme.NavyBlue

@Composable
fun PaymentHeader(
    state: ConfirmPaymentState
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            TitleText(
                modifier = Modifier.padding(top = 10.dp),
                text = "Confirmar pagamento",
                fontWeight = FontWeight.Bold,
                color = NavyBlue
            )

        HorizontalDivider(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(17.dp))
        BreezeIcon(
            modifier = Modifier
                .size(40.dp),
            breezeIcon = BreezeIcons.Linear.All.SpotifyLinear,
            contentDescription = "Spotify"
        )
        Spacer(modifier = Modifier.height(22.dp))
        BreezeRegularText(
            text = "R$ 300,00",
            size = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        DescriptionText("Pagamento referente a Spotify")

        Spacer(modifier = Modifier.height(30.dp))

    }
}