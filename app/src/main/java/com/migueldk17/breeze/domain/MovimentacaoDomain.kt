package com.migueldk17.breeze.domain

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.enums.TipoComparacao
import com.migueldk17.breeze.enums.TipoMovimentacao
import java.math.BigDecimal
import java.time.LocalDate

data class MovimentacaoDomain(

    val id: Long = 0L,

    val valor: BigDecimal,

    val descricao: String,

    val date: LocalDate,

    val icon: String,

    val colorIcon: Int = -9573033, //Valor padrão remete ao IconGreen do BreezeIcons
    val colorCard: Int = -1838339, // Valor padrão remete ao SoftBlue do BreezeIcons

    val tipo: TipoMovimentacao,

    val contaId: Long? = null
)
