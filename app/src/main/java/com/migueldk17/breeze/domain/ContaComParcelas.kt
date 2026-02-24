package com.migueldk17.breeze.domain

import com.migueldk17.breeze.data.local.entity.Conta
import com.migueldk17.breeze.data.local.entity.ParcelaEntity
import com.migueldk17.breeze.enums.StatusConta

data class ContaComParcelas(
    val conta: Conta,
    val parcelas: List<ParcelaEntity>,
    val status: StatusConta
)
