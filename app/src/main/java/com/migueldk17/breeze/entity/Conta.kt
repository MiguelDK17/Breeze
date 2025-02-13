package com.migueldk17.breeze.entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ColorConverter


@Entity(tableName = "conta_table")
data class Conta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "valor")
    val valor: Double,

    @TypeConverters(ColorConverter::class)
    @ColumnInfo(name = "color_icon")
    val colorIcon: Color,


)