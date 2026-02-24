package com.migueldk17.breeze.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.data.local.entity.Conta
import com.migueldk17.breeze.data.local.entity.ParcelaEntity
import com.migueldk17.breeze.enums.StatusConta
import java.time.LocalDate

data class ContaComParcelas(
    @Embedded val conta: Conta,

    @Relation(
        parentColumn = "id",
        entityColumn = "id_conta_pai"
    )

    val parcelas: List<ParcelaEntity>
)

fun ContaComParcelas.calcularStatusParcelado(
    parcelas: List<ParcelaEntity>,
    today: LocalDate
): StatusConta {
    if (parcelas.isEmpty()){
        return StatusConta.PENDENTE
    }

    val parcelasNaoPagas = parcelas.filter { !it.estaPaga }

    if (parcelasNaoPagas.isEmpty()) {
        return StatusConta.PAGA
    }

    val existeAtrasada = parcelasNaoPagas.any{
        it.dataDeVencimento.toLocalDate().isBefore(today)
    }

    return if (existeAtrasada) {
        StatusConta.ATRASADA
    } else {
        StatusConta.PENDENTE
    }
}