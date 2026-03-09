package com.migueldk17.breeze.ui.features.historico.ui.comparativo.mapper.domain

import com.migueldk17.breeze.converters.toDatabaseValue
import com.migueldk17.breeze.data.local.entity.MovimentacaoEntity
import com.migueldk17.breeze.domain.MovimentacaoDomain

fun MovimentacaoDomain.toEntity(): MovimentacaoEntity {
    return MovimentacaoEntity(
        id = id,
        valor = valor,
        descricao = descricao,
        data = date.toDatabaseValue(),
        icon = icon,
        tipo = tipo,
        contaId = contaId
    )
}