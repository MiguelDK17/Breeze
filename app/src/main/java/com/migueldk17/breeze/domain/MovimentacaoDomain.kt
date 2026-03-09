package com.migueldk17.breeze.domain

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.enums.TipoMovimentacao
import java.time.LocalDate

data class MovimentacaoDomain(

    val id: Long = 0L,

    val valor: Double,

    val descricao: String,

    val date: LocalDate,

    val icon: String,

    val tipo: TipoMovimentacao,

    val contaId: Long? = null
)
