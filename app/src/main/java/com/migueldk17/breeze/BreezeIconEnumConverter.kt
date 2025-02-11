package com.migueldk17.breeze

import androidx.room.TypeConverter
import com.github.migueldk17.breezeicons.icons.BreezeIconsEnum

class BreezeIconEnumConverter {
    @TypeConverter
    fun fromEnum(iconEnum: BreezeIconsEnum): String {
        return iconEnum.name
    }

    @TypeConverter
    fun toEnum(value: String): BreezeIconsEnum {
        return BreezeIconsEnum.valueOf(value)
    }
}