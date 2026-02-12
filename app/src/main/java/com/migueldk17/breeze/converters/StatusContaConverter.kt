package com.migueldk17.breeze.converters


import androidx.room.TypeConverter
import com.migueldk17.breeze.enums.StatusConta


fun fromStatus(status: StatusConta): Int = status.ordinal

fun Int.toStatus(): StatusConta {
       val statusConta = when(this) {
            0 -> StatusConta.PENDENTE
            1 -> StatusConta.PAGA
            2 -> StatusConta.ATRASADA
           else -> StatusConta.INVALIDO
       }
        return statusConta
    }