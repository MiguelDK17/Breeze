package com.migueldk17.breeze.ui.utils


import android.content.Context
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
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



