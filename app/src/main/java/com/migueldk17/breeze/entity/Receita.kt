package com.migueldk17.breeze.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saldo_table")
data class Receita(
    //Chave primária do banco de dados
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //Nome da coluna do SQLite
    @ColumnInfo(name = "valor")
    val valor: Double,

    @ColumnInfo(name = "descricao")
    val descricao: String,

    @ColumnInfo(name = "data")
    val data: String //Está sendo salvo como LocalDate.toString()
)
