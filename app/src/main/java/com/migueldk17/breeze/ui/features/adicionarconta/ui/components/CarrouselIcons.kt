package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.NavGraph2
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.theme.PastelLightBlue
import com.migueldk17.breeze.ui.theme.greyTextMediumPoppinsDarkMode
import com.migueldk17.breeze.ui.theme.greyTextMediumPoppinsLightMode
import com.migueldk17.breeze.ui.features.paginainicial.viewmodels.PaginaInicialViewModel
import kotlin.math.absoluteValue

@Composable
//Pega uma lista de icones e retorna o icone selecionado
fun carrouselIcons(iconList: List<BreezeIconsType>): BreezeIconsType{
    //PagerState pega a lista de ícones que será mandado para o horizontalPager
    //Começando pela página 2
    val pagerState = rememberPagerState(initialPage = 2) {
        iconList.size
    }
     Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(68.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(
                containerColor = if (!isSystemInDarkTheme()) Color.White else Color.Transparent,
            )
        ) {
            //HorizontalPager responsável pela seleção de ícones
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
                //Tamanho de cada página do HorizontalPager, pega um tamanho fixo
                pageSize = PageSize.Fixed(100.dp),
                //Padding para deixar o ícone selecionado no centro
                contentPadding = PaddingValues(horizontal = 136.dp),
                //Espaço de cada pagina, negativo para que não fique muito longe uma da outra
                pageSpacing = (-31).dp,

                ) { page -> //Página selecionada
                    //Tamanho máximo do ícone
                    val maxSize = 40.dp
                    //Tamanho mínimo do ícone
                    val minSize = 30.dp
                    // Calcula a proximidade do ícone ao centro
                    val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    val tolerance = 0.50f //Ajuste de tolerância para o scroll (quanto menor mais sensível)
                    val isCentered = pageOffset.absoluteValue < tolerance

                    //Scale reponsável por animar a troca dos ícones, entre selecionado e não selecionado
                    val scale = animateDpAsState(
                        targetValue = if (isCentered) maxSize else minSize,
                        animationSpec = tween(durationMillis = 300),
                        label = "iconAnimation"
                    )

                    val containerColor = verifyDarkMode(isCentered)

                    IconButton(onClick =
                    {

                    },
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
            color = if (!isSystemInDarkTheme()) greyTextMediumPoppinsLightMode else greyTextMediumPoppinsDarkMode
        )
    }
    //Retorna o ícone selecionado
    return iconList[pagerState.currentPage]
}

//Função que verifica o passo em que o navController está e apartir disso adiciona o ícone para a função correta do ViewModel
fun insereIconeNoViewModel(currentState: String?, viewModel: AdicionarContaViewModel, icone: BreezeIconsType){
    when(currentState) {
        //Caso passo 2 adiciona um icone a conta
        NavGraph2.Passo2.route -> {
            viewModel.guardaIconCard(icone)
        }
        //Caso passo 3 adiciona a cor do icone
        NavGraph2.Passo3.route -> {
            Log.d(TAG, "insereIconeNoViewModel: ${icone.color}")
            viewModel.guardaCorIconeEscolhida(icone)
        }
        //Caso passo 5 adiciona a cor do card da conta
        NavGraph2.Passo5.route -> {
            Log.d(TAG, "insereIconeNoViewModel: ${icone.color}")
            viewModel.guardaCorCardEscolhida(icone)
        }
        //Passo inválido
        else -> {
            Log.d(TAG, "verificaState: Passo inválido")
        }
    }
}

//Adiciona a cor padrão(Surface) baseado em qual rota o usuário está
fun adicionaCorPadrao(currentState: String?, viewModel: AdicionarContaViewModel){
    val colorIconDefault = NavyBlue
    val colorCardDefault = PastelLightBlue
    when(currentState) {
        //Caso esteja no Passo3 adiciona a cor ao icone
        NavGraph2.Passo3.route -> {
            viewModel.guardaCorIconePadrao(colorIconDefault)
        }
        //Caso esteja no Passo5 adiciona a cor ao card
        NavGraph2.Passo5.route -> {
            viewModel.guardaCorCardPadrao(colorCardDefault)
        }
    }
}
@Composable
private fun verifyDarkMode(isCentered: Boolean): Color {
    return when {
        !isSystemInDarkTheme() -> {
            if (isCentered) MaterialTheme.colorScheme.surface else Color.White
        }
        else -> {
            if (isCentered) DeepSkyBlue else Color.Transparent

        }
    }
}
