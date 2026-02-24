package com.migueldk17.breeze.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.migueldk17.breeze.enums.TipoMovimentacao

@Entity(tableName = "movimentacao_entity")
data class MovimentacaoEntity(
    //Chave primária do banco de dados
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    //Nome da coluna do SQLite
    @ColumnInfo(name = "valor")
    val valor: Double,

    @ColumnInfo(name = "descricao")
    val descricao: String,

    @ColumnInfo(name = "data")
    val data: String, //Está sendo salvo como LocalDate.toString()

    @ColumnInfo(name = "icon")
    val icon: String = "",

    @ColumnInfo("tipo")
    val tipo: TipoMovimentacao,

    val contaId: Long? = null
)
