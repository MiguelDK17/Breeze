package com.migueldk17.breeze.ui.components.adicionarconta

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.BreezeIcons
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.greyTextMediumPoppins

@Composable
fun CarrouselIcons(){
    val iconList = listOf(
        BreezeIcons.BookLinear,
        BreezeIcons.GroupLinear,
        BreezeIcons.GlobeLinear,
        BreezeIcons.CarLinear,
        BreezeIcons.CloudLinear,
        BreezeIcons.DropLinear,
        BreezeIcons.AirplaneLinear,
        BreezeIcons.DiscoverLinear,
        BreezeIcons.KeyLinear)
    val pagerState = rememberPagerState(initialPage = 2) {
        iconList.size
    }
    Column(
        modifier = Modifier
            .size(width = 382.dp, height = 129.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(width = 382.dp, height = 68.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                    pageSize = PageSize.Fixed(100.dp),
                contentPadding = PaddingValues(horizontal = 151.dp),
                pageSpacing = (-31).dp,

                ) { page ->
                    IconButton(onClick = { Log.d(TAG, "CarrouselIcons: $page")}) {
                        Icon(
                            iconList[page],
                            ""
                        )
                    }
                }




        }
        Spacer(Modifier.height(15.dp))
        Text("Arraste para o lado para selecionar",
            style = MaterialTheme.typography.bodyMedium,
            color = greyTextMediumPoppins)
    }

}
@Preview(showBackground = true)
@Composable
private fun Preview(){
    BreezeTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(PastelLightBlue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
         CarrouselIcons()
        }
    }
}