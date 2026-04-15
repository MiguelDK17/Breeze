package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.utils.formatarValorEmReal
import com.migueldk17.breeze.ui.utils.soften
import kotlinx.collections.immutable.persistentListOf
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun ObjectGraficoCategoria(
    colorCard: Color,
    icon: BreezeIconsType,
    iconColor: Color,
    nomeCategoria: String,
    valorConta: BigDecimal,
    porcentagem: String,
    progress: Float,
    progressBrush: Brush,
    modifier: Modifier = Modifier
){
    //Row pai
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        //Box Icon é a box que irá conter o icone da categoria
        BoxIcon(
            colorCard = colorCard,
            composable = {
                //Icone da categoria
                BreezeIcon(
                    breezeIcon = icon,
                    contentDescription = "Icone da categoria",
                    modifier = Modifier.size(28.dp),
                    color = iconColor
                )
            }
        )
        Column(
            modifier = Modifier.fillMaxHeight()
                .weight(1f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = nomeCategoria,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )

                    Text(
                        text = valorConta.formatarValorEmReal(),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.5.sp,
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                }
                BoxPorcentagem(
                    composable = {
                        Text(
                            text = porcentagem,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            )
                    }
                )
            }
            HighlightProgressBar(
                progress = progress,
                progressBrush = progressBrush,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp
                    ),
            )
        }
    }
}

@Composable
private fun BoxIcon(
    colorCard: Color,
    composable: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .size(42.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorCard
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            composable()
        }
    }
}

@Composable
private fun BoxPorcentagem(
    composable: @Composable () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .width(40.dp)
            .height(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0F0F8)
        )
    ) {
      Column(
          modifier = Modifier
              .fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ) {
          composable()
      }
    }
}

data class CategoriaObject(
    val nomeCategoria: String,
    val valorConta: BigDecimal,
    val porcentagem: String,
    val colorCard: Color,
    val icon: BreezeIconsType,
    val iconColor: Color,
    val progressBrush: Brush
)

@Composable
@Preview
private fun Preview(){
    BreezeTheme() {
        val alimentacao = CategoriaObject(
            nomeCategoria = "Alimentação",
            valorConta = BigDecimal(860.00),
            porcentagem = "31%",
            colorCard = Color(0xFFFCE3D0),
            icon = BreezeIcons.Linear.FoodKitchen.Donut,
            iconColor = Color(0xFF87480F),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFFFC9438), Color(0xFFFC9438).soften()))
        )
        val transporte = CategoriaObject(
            nomeCategoria = "Transporte",
            valorConta = BigDecimal(570.00),
            porcentagem = "20%",
            colorCard = Color(0xFFFEBF39),
            icon = BreezeIcons.Linear.Mobility.CarLinear,
            iconColor = Color(0xFF742E01),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFFFEBF39), Color(0xFFFEBF39).soften()))

        )
        val moradia = CategoriaObject(
            nomeCategoria = "Moradia",
            valorConta = BigDecimal(450.00),
            porcentagem = "16%",
            colorCard = Color(0xFFDCF5F4),
            icon = BreezeIcons.Linear.Building.House,
            iconColor = Color(0xFF3BC2BE),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFF3BC2BE), Color(0xFF3BC2BE).soften()))

        )
        val lazer = CategoriaObject(
            nomeCategoria = "Lazer",
            valorConta = BigDecimal(350.00),
            porcentagem = "13%",
            colorCard = Color(0xFFEAE3FD),
            icon = BreezeIcons.Linear.All.Game,
            iconColor = Color(0xFF5333E9),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFF5333E9), Color(0xFF5333E9).soften()))

        )

        val saude = CategoriaObject(
            nomeCategoria = "Saúde",
            valorConta = BigDecimal(260.00),
            porcentagem = "9%",
            colorCard = Color(0xFFDDEEFD),
            icon = BreezeIcons.Linear.All.Heart,
            iconColor = Color(0xFF1A68DC),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFF5DA3EC), Color(0xFF5DA3EC).soften()))

        )

        val compras = CategoriaObject(
            nomeCategoria = "Compras",
            valorConta = BigDecimal(190.00),
            porcentagem = "7%",
            colorCard = Color(0xFFFEEAF3),
            icon = BreezeIcons.Linear.Shop.Bag2,
            iconColor = Color(0xFFD61350),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFFEB72A2), Color(0xFFEB72A2).soften()))

        )

        val assinaturas = CategoriaObject(
            nomeCategoria = "Assinaturas",
            valorConta = BigDecimal(140.00),
            porcentagem = "5%",
            colorCard = Color(0xFFEAE3FD),
            icon = BreezeIcons.Linear.VideoAudioImage.VideoCircleLinear,
            iconColor = Color(0xFF5328DC),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFF9C83EB), Color(0xFF9C83EB).copy(alpha = 0.15f)))

        )
        val educacao = CategoriaObject(
            nomeCategoria = "Educação",
            valorConta = BigDecimal(110.00),
            porcentagem = "4%",
            colorCard = Color(0xFFE0F6E2),
            icon = BreezeIcons.Linear.School.SquareAcademicCap2,
            iconColor = Color(0xFF05A542),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFF6BCF92), Color(0xFF6BCF92).copy(alpha = 0.15f)))

        )
        val pets = CategoriaObject(
            nomeCategoria = "Pets",
            valorConta = BigDecimal(80.00),
            porcentagem = "3%",
            colorCard = Color(0xFFFEE9D8),
            icon = BreezeIcons.Linear.Essetional.Paw,
            iconColor = Color(0xFFEA3B00),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFFFE9C36), Color(0xFFFE9C36).copy(alpha = 0.15f)))
        )
        val outros = CategoriaObject(
            nomeCategoria = "Outros",
            valorConta = BigDecimal(50.00),
            porcentagem = "2%",
            colorCard = Color(0xFFEFEFFA),
            icon = BreezeIcons.Linear.All.GlobeLinear,
            iconColor = Color(0xFF304E99),
            progressBrush = Brush.horizontalGradient(persistentListOf(Color(0xFF304E99), Color(0xFF304E99).copy(alpha = 0.15f)))
        )
        val list = persistentListOf(alimentacao, transporte, moradia, lazer, saude, compras, assinaturas, educacao, pets, outros)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier
                    .width(349.dp)

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                        .background(Color.White)
                ) {
                    items(
                        items = list,
                        key = { it.nomeCategoria}
                    ){ categoria ->
                        ObjectGraficoCategoria(
                            colorCard = categoria.colorCard,
                            icon = categoria.icon,
                            iconColor = categoria.iconColor,
                            nomeCategoria = categoria.nomeCategoria,
                            valorConta = categoria.valorConta,
                            porcentagem = categoria.porcentagem,
                            progress = categoria.valorConta.divide(BigDecimal(3060.00), 4, RoundingMode.HALF_UP).toFloat(),
                            progressBrush = categoria.progressBrush
                        )
                        Spacer(modifier = Modifier.size(25.dp))
                    }
                }
            }
        }




    }
}