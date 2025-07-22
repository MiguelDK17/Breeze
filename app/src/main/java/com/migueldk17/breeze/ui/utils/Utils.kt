package com.migueldk17.breeze.ui.utils


import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDate
import java.util.Locale
import kotlin.math.pow

fun formataSaldo(valor: Double?): String {
    if (valor != null) {
        val formatacao = String.format(Locale.getDefault(),"R$ %.2f", valor)
        return formatacao
    }
    return "Carregando..."
}

fun formataValorConta(valor: Double?): String = String.format(Locale.getDefault(), "R$: %.2f", valor)

fun traduzData(mes: String): String {

    val mesTraduzido = when(mes){
        "Jan" ->  "Janeiro"
        "Fev" ->  "Fevereiro"
        "Mar" ->  "Março"
        "Abr" ->  "Abril"
        "Mai" ->  "Maio"
        "Jun" ->  "Junho"
        "Jul" ->  "Julho"
        "Ago" ->  "Agosto"
        "Set" ->  "Setembro"
        "Out" ->  "Outubro"
        "Nov" ->  "Novembro"
        "Dez" ->  "Dezembro"
        else -> "Inválido"
    }
    return mesTraduzido
}

fun arredondarValor(valor: Double, casasDecimais: Int = 2): Double {
    return BigDecimal(valor)
        .setScale(casasDecimais, RoundingMode.HALF_EVEN)
        .toDouble()
}

fun formataMesAno(localDate: LocalDate): String {
    return "%04d-%02d".format(localDate.year, localDate.monthValue)
}

//fun calculaValorTotalConta(
//    valorParcela: Double,
//    taxaJurosMensal: Double,
//    qtdParcelas: Int
//): Double {
//    if (qtdParcelas <= 0 || taxaJurosMensal < 0) return 0.0
//
//    val i = taxaJurosMensal
//    val n = qtdParcelas
//
//    return if (i == 0.0){
//        valorParcela * n
//    } else {
//        valorParcela * (1 - (1 + i).pow(-n)) / i
//    }
//}
fun formataTaxaDeJuros(taxaDeJurosMensal: Double): String {
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



