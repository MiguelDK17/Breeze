package com.migueldk17.breeze.ui.features.historico.ui.comparativo.mapper.entity

import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.data.local.entity.MovimentacaoEntity
import com.migueldk17.breeze.domain.MovimentacaoDomain

fun MovimentacaoEntity.toDomain(): MovimentacaoDomain {
    return MovimentacaoDomain(
        id = id,
        valor = valor,
        descricao = descricao,
        date = data.toLocalDate(),
        icon = icon,
        tipo = tipo,
        contaId = contaId

    )
}