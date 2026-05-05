package com.migueldk17.breeze.domain

import com.migueldk17.breeze.data.local.entity.ContaEntity
import com.migueldk17.breeze.data.local.entity.ParcelaEntity
import com.migueldk17.breeze.enums.StatusConta

data class ContaComParcelas(
    val contaEntity: ContaEntity,
    val parcelas: List<ParcelaEntity>,
    val status: StatusConta
)
