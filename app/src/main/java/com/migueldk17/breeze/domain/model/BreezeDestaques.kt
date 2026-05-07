package com.migueldk17.breeze.domain.model

import com.migueldk17.breeze.domain.ContaComParcelas
import com.migueldk17.breeze.domain.MovimentacaoDomain

data class BreezeDestaques(
    val maiorDespesa: MovimentacaoDomain,
    val maiorReceita: MovimentacaoDomain
)
