package com.migueldk17.breeze.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saldo_table")
data class Saldo(
    //Chave primária do banco de dados
    @PrimaryKey(autoGenerate = true) //autoGenerate vai gerar um valor automárito ao id
    val id: Int = 0,
    //Nome da coluna do SQLite
    @ColumnInfo(name = "valor")
    val valor: Double
)