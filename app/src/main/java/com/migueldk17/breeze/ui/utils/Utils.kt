package com.migueldk17.breeze.ui.utils


import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.util.Locale

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
        "JANUARY" ->  "Janeiro"
        "FEBRUARY" ->  "Fevereiro"
        "MARCH" ->  "Março"
        "APRIL" ->  "Abril"
        "MAY" ->  "Maio"
        "JUNE" ->  "Junho"
        "JULY" ->  "Julho"
        "AUGUST" ->  "Agosto"
        "SEPTEMBER" ->  "Setembro"
        "OCTOBER" ->  "Outubro"
        "NOVEMBER" ->  "Novembro"
        "DECEMBER" ->  "Dezembro"
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



