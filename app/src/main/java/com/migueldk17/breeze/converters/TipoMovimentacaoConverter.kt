package com.migueldk17.breeze.converters

import androidx.room.TypeConverter
import com.migueldk17.breeze.enums.TipoMovimentacao

class TipoMovimentacaoConverter {
    @TypeConverter
    fun fromInt(value: Int): TipoMovimentacao = TipoMovimentacao.fromInt(value)

    @TypeConverter
    fun toInt(tipo: TipoMovimentacao): Int = TipoMovimentacao.toInt(tipo)
}