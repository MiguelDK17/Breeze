package com.migueldk17.breeze

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter

object ColorConverter {
        @JvmStatic
        @TypeConverter
        fun fromColor(color: Color): Int {
            return color.toArgb()
        }
        @JvmStatic
        @TypeConverter
        fun toColor(value: Int): Color {
            return Color(value)
        }
    }
