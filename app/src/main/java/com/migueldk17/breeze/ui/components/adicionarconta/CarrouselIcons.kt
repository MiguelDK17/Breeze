package com.migueldk17.breeze.ui.components.adicionarconta

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.greyTextMediumPoppins
import kotlin.math.absoluteValue

@Composable
fun CarrouselIcons(iconList: List<BreezeIconsType>){
    val pagerState = rememberPagerState(initialPage = 2) {
        iconList.size
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(68.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                    pageSize = PageSize.Fixed(100.dp),
                contentPadding = PaddingValues(horizontal = 136.dp),
                pageSpacing = (-31).dp,

                ) { page ->
                    val maxSize = 40.dp
                    val minSize = 30.dp
                    // Calcula a proximidade do ícone ao centro
                    val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    val tolerance = 0.50f //Ajuste de tolerância para o scroll (quanto menor mais sensível)
                    val isCentered = pageOffset.absoluteValue < tolerance

                    val scale = animateDpAsState(
                        targetValue = if (isCentered) maxSize else minSize,
                        animationSpec = tween(durationMillis = 300),
                        label = "iconAnimation"
                    )

                    val containerColor = if(isCentered) MaterialTheme.colorScheme.surface else Color.White



                    IconButton(onClick = { Log.d(TAG, "CarrouselIcons: $page")},
                        modifier = Modifier
                            .size(scale.value)
                            .graphicsLayer {
                                // Adiciona um leve efeito de elevação ao ícone central
                                alpha = 2.5f - pageOffset.absoluteValue
                                scaleX = scale.value / 30.dp
                                scaleY = scale.value / 30.dp
                            },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = containerColor,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            disabledContentColor = Color.Transparent,
                        )
                        ) {

                        BreezeIcon(
                            iconList[page],
                            contentDescription = null
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
    val iconList = listOf(
        BreezeIcons.Linear.BookLinear,
        BreezeIcons.Linear.GroupLinear,
        BreezeIcons.Linear.GlobeLinear,
        BreezeIcons.Linear.CarLinear,
        BreezeIcons.Linear.CloudLinear,
        BreezeIcons.Linear.DropLinear,
        BreezeIcons.Linear.AirplaneLinear,
        BreezeIcons.Linear.DiscoverLinear,
        BreezeIcons.Linear.KeyLinear)

    BreezeTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(PastelLightBlue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
         CarrouselIcons(iconList)
        }
    }
}