package com.migueldk17.breeze.ui.utils

import android.content.ContentValues.TAG
import android.util.Log
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
