package com.migueldk17.breeze.converters

import androidx.room.TypeConverter
import com.migueldk17.breeze.ui.utils.StatusConta

class StatusContaConverter {

    @TypeConverter
    fun fromStatus(status: StatusConta): Int = status.ordinal

    @TypeConverter
    fun toStatus(value: Int): StatusConta =
        StatusConta.entries[value]
}