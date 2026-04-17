package com.migueldk17.breeze.ui.utils


import android.content.Context
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.pow

fun formataSaldo(valor: BigDecimal?): String {
    return valor?.formatarValorEmReal() ?: "Carregando..."
}

fun parseCentavos(valor: String): BigDecimal {
    val numero = valor.toBigDecimalOrNull() ?: BigDecimal.ZERO
    return numero
        .divide(BigDecimal(100))
        .setScale(2, RoundingMode.HALF_EVEN)
}
fun BigDecimal.formatarValorEmReal(): String {
    Log.d(TAG, "formatarValorEmReal: Antes da formatacao $this")
    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return format.format(this)
}

fun LocalDate.toApiFormat(): String {
    return format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}

fun traduzData(mes: String): String {

    val mesTraduzido = when (mes) {
        "Jan", "January" -> "Janeiro"
        "Fev", "February" -> "Fevereiro"
        "Mar", "March" -> "Março"
        "Abr", "April" -> "Abril"
        "Mai", "May" -> "Maio"
        "Jun", "June" -> "Junho"
        "Jul", "July" -> "Julho"
        "Ago", "August" -> "Agosto"
        "Set", "September" -> "Setembro"
        "Out", "October" -> "Outubro"
        "Nov", "November" -> "Novembro"
        "Dez", "December" -> "Dezembro"
        else -> "Inválido, palavra que veio: $mes"
    }
    return mesTraduzido
}

object MoneyUtils {
    fun arredondarValor(valor: BigDecimal, casasDecimais: Int = 2): BigDecimal {
        return valor
            .setScale(casasDecimais, RoundingMode.HALF_EVEN)

    }

    fun calcularPorcentagem(
        valorCategoria: BigDecimal,
        valorTotal: BigDecimal
    ): BigDecimal {
        if (valorTotal == BigDecimal.ZERO) return BigDecimal.ZERO

        return valorCategoria
            .divide(valorTotal, 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal(100))
    }
}


fun formataMesAno(localDate: LocalDate): String {
    return "%04d-%02d".format(localDate.year, localDate.monthValue)
}

fun formataTaxaDeJuros(taxaDeJurosMensal: BigDecimal): String {
    val formato = DecimalFormat("#.##%") // Define a máscara: duas casas decimais e símbolo de porcentagem
    return formato.format(taxaDeJurosMensal)
}

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route){
        popUpTo(this@navigateSingleTopTo.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    }

object ToastManager {
    private var lastToastTime = 0L
    private val toastInterval = 2000

    fun showToast(context: Context, message: String) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastToastTime > toastInterval) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            lastToastTime = currentTime
        }
    }
}

fun retornaDataFormatadaParaPesquisaNoRoom(mes: String, ano: Int): String {
    val mesesMap = mapOf( // Use immutable mapOf
        "Jan" to "01", "Fev" to "02", "Mar" to "03", "Abr" to "04",
        "Mai" to "05", "Jun" to "06", "Jul" to "07", "Ago" to "08",
        "Set" to "09", "Out" to "10", "Nov" to "11", "Dez" to "12"
    )
    val dataFormatada = "$ano-${mesesMap[mes]}%"
    return dataFormatada
}

fun Color.soften(): Color {
    return Color(
        red = red + (1f - red) * 0.35f,
        green = green + (1f - green) * 0.35f,
        blue = blue + (1f - blue) * 0.35f,
        alpha = 1f
    )
}

object CategoryColorProvider {
     fun returnIconColor(category: String): Color {
        return when(category) {
            "Alimentação" -> Color(0xFF87480F)
            "Transporte" -> Color(0xFF742E01)
            "Educação" -> Color(0xFF05A542)
            "Moradia" -> Color(0xFF3BC2BE)
            "Lazer" -> Color(0xFF5333E9)
            "Saúde" -> Color(0xFF1A68DC)
            "Trabalho/Negócios" -> Color(0xFF2F94F4)
            "Pets" -> Color(0xFFEA3B00)
            "Pessoais" -> Color(0xFFD61350)
            "Outros" -> Color(0xFF304E99)
            else -> Color.White
        }
    }

    fun returnCardColor(category: String): Color {
        return when(category) {
            "Alimentação" -> Color(0xFFFCE3D0)
            "Transporte" -> Color(0xFFFEEFCB)
            "Educação" -> Color(0xFFE0F6E2)
            "Moradia" -> Color(0xFFDCF5F4)
            "Lazer" -> Color(0xFFEAE3FD)
            "Saúde" -> Color(0xFFDDEEFD)
            "Trabalho/Negócios" -> Color(0xFFDFEBFC)
            "Pets" -> Color(0xFFFEE9D8)
            "Pessoais" -> Color(0xFFFCD0E7)
            "Outros" -> Color(0xFFEFEFFA)
            else -> Color.White
        }
    }
     fun returnBrush(category: String): Brush {
        return when(category) {
            "Alimentação" -> Brush.horizontalGradient(persistentListOf(Color(0xFFFC9438), Color(0xFFFC9438).soften()))
            "Transporte" -> Brush.horizontalGradient(persistentListOf(Color(0xFFFEBF39), Color(0xFFFEBF39).soften()))
            "Educação" -> Brush.horizontalGradient(persistentListOf(Color(0xFF6BCF92), Color(0xFF6BCF92).soften()))
            "Moradia" -> Brush.horizontalGradient(persistentListOf(Color(0xFF3BC2BE), Color(0xFF3BC2BE).soften()))
            "Lazer" -> Brush.horizontalGradient(persistentListOf(Color(0xFF5333E9), Color(0xFF5333E9).soften()))
            "Saúde" -> Brush.horizontalGradient(persistentListOf(Color(0xFF5DA3EC), Color(0xFF5DA3EC).soften()))
            "Trabalho/Negócios" -> Brush.horizontalGradient(persistentListOf(Color(0xFF2F94F4), Color(0xFF2F94F4).soften()))
            "Pets" -> Brush.horizontalGradient(persistentListOf(Color(0xFFFE9C36), Color(0xFFFE9C36).soften()))
            "Pessoais" -> Brush.horizontalGradient(persistentListOf(Color(0xFFD61350), Color(0xFFD61350).soften()))
            "Outros" -> Brush.horizontalGradient(persistentListOf(Color(0xFF304E99), Color(0xFF304E99).soften()))
            else -> Brush.horizontalGradient(persistentListOf(Color(0xFF304E99), Color(0xFF304E99).soften()))
        }
    }





}

object CategoryIconProvider {

    val icons = persistentMapOf(
        "Alimentação" to "DONUT",
        "Transporte" to "CAR_LINEAR",
        "Moradia" to "HOUSE_LINEAR",
        "Lazer" to "GAME_LINEAR",
        "Saúde" to "HEART_LINEAR",
        "Trabalho/Negocios" to "BAG2_LINEAR",
        "Entretenimento" to "VIDEO_CIRCLE_LINEAR",
        "Educação" to "SQUARE_ACADEMIC_CAP_2_LINEAR",
        "Pets" to "PAW",
        "Outros" to "GLOBE_LINEAR"
    )

    fun getIcon(category: String): String {
        return icons[category] ?: "GLOBE_LINEAR"
    }
}



