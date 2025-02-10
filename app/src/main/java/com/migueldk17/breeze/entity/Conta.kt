package com.migueldk17.breeze.entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.migueldk17.breezeicons.icons.BreezeIconsType

@Entity(tableName = "conta_table")
data class Conta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "valor")
    val valor: Double,

    @ColumnInfo(name = "icon")
    val icon: BreezeIconsType,

    @ColumnInfo(name = "color_icon")
    val colorIcon: Color,

    @ColumnInfo(name = "color_card")
    val colorCard: Color
)