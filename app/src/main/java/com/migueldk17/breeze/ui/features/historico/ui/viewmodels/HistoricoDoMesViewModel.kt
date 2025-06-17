package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.utils.formataMesAno
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HistoricoDoMesViewModel @Inject constructor(
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository
): ViewModel() {
    //Pega a data do mes
    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data.asStateFlow()
    //Pega as contas do mes sem filtro
    fun getContasDoMes(): Flow<List<Conta>?> {
        return contaRepository.getContasPorMes(_data.value)
    }


    val historico: StateFlow<List<HistoricoDoDia>> = _data
        .flatMapLatest { mes ->
            val dataFormadadaParaParcela = formataMesAno(LocalDate.now()) + "%"
            val contasFlow = contaRepository.getContasPorMes(mes.take(3))
            val parcelasFlow = parcelaRepository.buscaTodasAsParcelasDoMes(dataFormadadaParaParcela)


            combine(contasFlow, parcelasFlow) { contas, parcelas ->

                val contasMapeadas = contas.associateBy {it.id}

               val idsContaPai = parcelas.map { it.idContaPai }.toSet()

                val contasFiltradas = contas.filterNot { it.id in idsContaPai }

                val contasDasParcelas = parcelas.mapNotNull { parcela ->
                    val contaPai = contasMapeadas[parcela.idContaPai]
                    contaPai?.let {
                        Conta(
                            id = parcela.id.toLong(),
                            name = "${it.name} - Parcela ${parcela.numeroParcela}/${parcela.totalParcelas}",
                            categoria = it.categoria,
                            subCategoria = it.subCategoria,
                            valor = parcela.valor,
                            icon = it.icon,
                            colorIcon = it.colorIcon,
                            colorCard = it.colorCard,
                            dateTime = parcela.data.toLocalDate().atStartOfDay().toString(),
                            isContaParcelada = true
                        )
                    }
                }
                val todasAsContas = contasFiltradas + contasDasParcelas

                todasAsContas
                    .sortedBy { it.dateTime.toLocalDateTime() }
                    .groupBy { it.dateTime.toLocalDateTime().toLocalDate() }
                    .mapNotNull { (data, contasDoDia) ->
                        val contasOrdenadas = contasDoDia.sortedByDescending { it.dateTime }
                        val primeira = contasOrdenadas.first()
                        val outras = contasOrdenadas.drop(1)

                        HistoricoDoDia(
                            data = data,
                            contaPrincipal = primeira,
                            outrasContas = outras
                        )
                    }
                    .sortedByDescending { it.data }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    //Pega as primeiras três letras do mês
    fun setData(mes: String){
        _data.value = mes.take(3)
    }
}
