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

fun formatarNomeConta(nome: String, maxChar: Int): String {
     val palavras = nome.trim().split(" ")
    Log.d(TAG, "formatarNomeConta: $nome $maxChar")

    return when {
        palavras.size == 1 -> {
            //Nome simples: Limitar até 11 letras

            if (palavras[0].length >= 5) palavras[0].take(5) else palavras[0].take(5)
        }
        palavras.size == 2 && maxChar >= 11-> {
            //Nome composto: abreviar a primeira palavra
            Log.d(TAG, "formatarNomeConta: tá caindo no else, tamamanho da palavra: ${palavras.size}")
            val primeira = palavras[0].take(3) + "."
            val resto = palavras.drop(1).joinToString("")
            val palavraCompleta = "$primeira $resto"
            Log.d(TAG, "formatarNomeConta: O vivo easy deveria estar aqui: $palavraCompleta")
            palavraCompleta
        }
        palavras.size == 2 && maxChar < 11 -> {
            nome
        }
        else -> {
            Log.d(TAG, "formatarNomeConta: tá caindo no else, tamamanho da palavra: ${palavras.size}")
            val primeira = palavras[0].take(3) + "."
            val meio = if (palavras[1].length >= 3) "${palavras[1].take(3)} ${palavras.drop(2).joinToString("")}" else "${palavras[1].take(2)} ${palavras.drop(2).joinToString("")}"
            "$primeira $meio"
        }
    }

}

