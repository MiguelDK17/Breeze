package com.migueldk17.breeze.data.local.entity


import android.util.Log
import android.content.ContentValues.TAG
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.migueldk17.breeze.enums.StatusConta
import java.time.LocalDate


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

    @ColumnInfo(name = "data_pagamento")
    val dataPagamento: String? = null,

    @ColumnInfo(name = "data_vencimento")
    val dataVencimento: String? = null,

    @ColumnInfo(name = "forma_de_pagamento")
    val formaDePagamento: String? = null,

    @ColumnInfo(name = "is_conta_parcelada", defaultValue = "0")
    val isContaParcelada: Boolean = false // Booleano pra saber se a conta é parcelada ou não
)

fun Conta.calcularStatus(today: LocalDate, dataPagamento: String?, dataVencimento: String?): StatusConta {

     return when {
        dataPagamento != null -> {
            Log.d(TAG, "calcularStatus: Deu como paga")
            StatusConta.PAGA
        }
        dataVencimento!! < today.toString() -> {
            Log.d(TAG, "calcularStatus: Deu como atrasada")
            StatusConta.ATRASADA
        }
        else -> {
            Log.d(TAG, "calcularStatus: Caiu no else, como PENDENTE")
            StatusConta.PENDENTE
        }

    }
}


