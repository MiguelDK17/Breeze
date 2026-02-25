package com.migueldk17.breeze.domain

import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.data.local.repository.ContaRepository
import com.migueldk17.breeze.data.local.repository.ParcelaRepository
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObservarContasDoMesUseCase @Inject constructor(
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository
) {
    operator fun invoke(mes: String): Flow<List<LinhaDoTempoModel>> {
        //Flow de Lista de Contas
        val contasFlow = contaRepository.getContasMes(mes)
        //Flow de Lista de Contas
        val parcelasFlow = parcelaRepository.buscaTodasAsParcelasDoMes(mes)
        //Combine junta os fluxos de contas e parcelas
        return combine(contasFlow, parcelasFlow) { contas, parcelas ->
            //Mapeia as contas baseado no id
            val contasMapeadas = contas.associateBy { it.id }

            //Cria um set com os IDs das contas que são pais de parcelas no mês
            val idsContaPai = parcelas.map { it.idContaPai }.toSet()

            //Filtra as contas normais
            // - removendo as contas que são pai de parcela
            // - remove contas parceladas cuja parcela não caiu nesse mês
            val contasDoMes = contas.map {
                LinhaDoTempoModel(
                    id = it.id,
                    name = it.name,
                    category = it.categoria,
                    subCategory = it.subCategoria,
                    valor = it.valor,
                    icon = it.icon,
                    colorIcon = it.colorIcon,
                    colorCard = it.colorCard,
                    dateTime = it.dateTime.toLocalDateTime(),
                )
            }
                .filterNot { it.id in idsContaPai }
                .filterNot { it.isContaParcelada }

            //Agora monta as contas representando as parcelas
            val parcelasDoMes = parcelas.mapNotNull { parcela ->

                val contaPai = contasMapeadas[parcela.idContaPai]
                    ?: contaRepository.getContaById(parcela.idContaPai)

                contaPai?.let { pai ->
                    LinhaDoTempoModel(
                        id = parcela.id,
                        name = "${pai.name} - Parcela ${parcela.numeroParcela}/${parcela.totalParcelas}",
                        category = pai.categoria,
                        subCategory = pai.subCategoria,
                        valor = parcela.valor,
                        icon = pai.icon,
                        colorIcon = pai.colorIcon,
                        colorCard = pai.colorCard,
                        dateTime = parcela.dataDeVencimento.toLocalDate()
                            .atStartOfDay(),
                    )
                }
            }
            (contasDoMes + parcelasDoMes).sortedBy { it.dateTime }
        }
    }
}
