package com.migueldk17.breeze.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.migueldk17.breeze.ui.utils.StatusConta


@Entity(tableName = "conta_table")
data class Conta(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, //Id da conta

    @ColumnInfo(name = "name")
    val name: String, //Nome da conta

    @ColumnInfo(name = "category") //Categoria da Conta
    val categoria: String,

    @ColumnInfo(name = "sub_category") //Sub categoria da Conta
    val subCategoria: String,

    @ColumnInfo(name = "valor")
    val valor: Double, //Valor monetário da conta

    @ColumnInfo(name = "icon")
    val icon: String, //Referencia do icone de tipo BreezeIconsType

    @ColumnInfo(name = "color_icon")
    val colorIcon: Int, //Cor do icone já transformada em Int

    @ColumnInfo(name = "color_card")
    val colorCard: Int, //Cor do card já transformada em Int

    @ColumnInfo(name = "date_time")
    val dateTime: String, //Data e gora de criação da conta

    @ColumnInfo(name = "status", defaultValue = "0")
    val status: Int = StatusConta.PENDENTE.ordinal,

    @ColumnInfo(name = "data_pagamento")
    val dataPagamento: String? = null,

    @ColumnInfo(name = "is_conta_parcelada", defaultValue = "0")
    val isContaParcelada: Boolean = false // Booleano pra saber se a conta é parcelada ou não
)


