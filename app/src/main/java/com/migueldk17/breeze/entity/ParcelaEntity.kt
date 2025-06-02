package com.migueldk17.breeze.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parcela_entity")
data class ParcelaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, //Id da parcela

    @ColumnInfo(name = "id_conta_pai")
    val idContaPai: Long, //Id da conta pai

    @ColumnInfo(name = "valor")
    val valor: Double, //Valor das parcelas

    @ColumnInfo(name = "porcentagem_juros")
    val porcentagemJuros: Double,

    @ColumnInfo(name = "numero_parcela")
    val numeroParcela: Int, //Número da parcela

    @ColumnInfo(name = "total_parcelas")
    val totalParcelas: Int, //Total de parcelas

    @ColumnInfo(name = "data")
    val data: String, //Data das parcelas

    @ColumnInfo(name = "esta_paga", defaultValue = "0")
    val estaPaga: Boolean = false //Booleano de verificação caso a parcela esteja paga
)
