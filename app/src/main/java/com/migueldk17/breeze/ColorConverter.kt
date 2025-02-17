package com.migueldk17.breeze

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter

class ColorConverter {
        companion object {
            @TypeConverter
            @JvmStatic
            fun fromColor(color: Color): Int {
                return color.toArgb()
            }

            @TypeConverter
            @JvmStatic
            fun toColor(value: Int): Color {
                return Color(value)
            }
        }
    }
