package com.migueldk17.breeze.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "parcela_entity")
data class ParcelaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "id_conta_pai")
    val idContaPai: String,

    @ColumnInfo(name = "valor")
    val valor: Double,

    @ColumnInfo(name = "numero_parcela")
    val numeroParcela: Int,

    @ColumnInfo(name = "total_parcelas")
    val totalParcelas: Int,

    @ColumnInfo(name = "data")
    val data: String,

    @ColumnInfo(name = "esta_paga", defaultValue = "0")
    val estaPaga: Boolean = false
)
