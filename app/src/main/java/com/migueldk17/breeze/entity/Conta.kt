package com.migueldk17.breeze.entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "conta_table")
data class Conta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "valor")
    val valor: Double,

    @ColumnInfo(name = "icon")
    val icon: String,

    @ColumnInfo(name = "color_icon")
    val colorIcon: Int,

    @ColumnInfo(name = "color_card")
    val colorCard: Int

)

