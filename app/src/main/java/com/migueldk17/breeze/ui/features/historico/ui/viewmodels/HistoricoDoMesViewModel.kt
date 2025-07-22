package com.migueldk17.breeze.ui.features.historico.ui.viewmodels

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    private val _contasPorMes = MutableStateFlow<List<LinhaDoTempoModel>>(emptyList())
    val contasPorMes: StateFlow<List<LinhaDoTempoModel>> = _contasPorMes.asStateFlow()

    private val _listaVazia = MutableStateFlow(false)
    val listaVazia: StateFlow<Boolean> = _listaVazia.asStateFlow()


    private val _parcela: MutableStateFlow<UiState<ParcelaEntity>> = MutableStateFlow(UiState.Loading)
    val parcela: StateFlow<UiState<ParcelaEntity>> = _parcela.asStateFlow()


    fun observarContaPorMes() {
        viewModelScope.launch {
            _data
                //Filtra caso a data seja vazia
                .filter { it.isNotBlank() && it.matches(Regex("""\d{4}-\d{2}%""")) }
                .collectLatest { mes ->
                    val contasFlow = contaRepository.getContasMes(mes)
                    val parcelasFlow = parcelaRepository.buscaTodasAsParcelasDoMes(mes)

                    combine(contasFlow, parcelasFlow) { contas, parcelas ->
                        //Mapeia as contas baseado no id
                        val contasMapeadas = contas.associateBy { it.id }
                        val idsContaPai = parcelas.map { it.idContaPai }.toSet()
                        val transformaContaEmLinhaDoMesViewModel = contas.map {
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

                        val contasDasParcelas = parcelas.mapNotNull { parcela ->
                            val contaPai = contasMapeadas[parcela.idContaPai]
                                ?: contaRepository.getContaById(parcela.idContaPai)
                            contaPai?.let { contaPai ->
                                LinhaDoTempoModel(
                                    id = parcela.id.toLong(),
                                    name = "${contaPai.name} - Parcela ${parcela.numeroParcela}/${parcela.totalParcelas}",
                                    category = contaPai.categoria,
                                    subCategory = contaPai.subCategoria,
                                    valor = parcela.valor,
                                    icon = contaPai.icon,
                                    colorIcon = contaPai.colorIcon,
                                    colorCard = contaPai.colorCard,
                                    dateTime = parcela.data.toLocalDate().atStartOfDay(),
                                )
                            }
                        }
                        val todasAsContas = transformaContaEmLinhaDoMesViewModel + contasDasParcelas
                        todasAsContas.sortedBy { it.dateTime }
                    }.collectLatest { contasOrdenadas ->
                        _contasPorMes.value = contasOrdenadas
                        _listaVazia.value = contasOrdenadas.isEmpty()
                    }
            }
        }
    }

     fun buscaParcelaPorId(idParcela: Long): UiState<ParcelaEntity> {
           viewModelScope.launch {
             parcelaRepository.getParcelaPorId(idParcela)
                  .let{ parcela ->
                      if (parcela == null){
                          _parcela.value = UiState.Empty
                      }
                      else {
                          _parcela.value = UiState.Success(parcela)
                      }
                  }

          }
         return _parcela.value
     }



    val historico: StateFlow<List<HistoricoDoDia>> = _data
        .flatMapLatest { mes ->
            //Flow de Lista de Contas
            val contasFlow = contaRepository.getContasMes(mes)
            //Flow de Lista de Contas
            val parcelasFlow = parcelaRepository.buscaTodasAsParcelasDoMes(mes)

            //Combine junta os fluxos de contas e parcelas
            combine(contasFlow, parcelasFlow) { contas, parcelas ->
                //Mapeia as contas baseado no id
                val contasMapeadas = contas.associateBy {it.id}

                //Cria um Set com os IDs das contas que são pais de parcelas no mês
                val idsContaPai = parcelas.map { it.idContaPai }.toSet()

                //Filtra as contas normais
                // - removendo as contas que são pai de parcela
                // - remove contas parceladas cuja parcela não caiu nesse mês
                val transformaContaEmLinhaDoMesViewModel = contas.map { conta ->
                    LinhaDoTempoModel(
                        id = conta.id,
                        name = conta.name,
                        category = conta.categoria,
                        subCategory = conta.subCategoria,
                        valor = conta.valor,
                        icon = conta.icon,
                        colorIcon = conta.colorIcon,
                        colorCard = conta.colorCard,
                        dateTime = conta.dateTime.toLocalDateTime(),
                    )
                }
                    .filterNot { it.id in idsContaPai }
                    .filterNot { it.isContaParcelada }

                //Agora monta as contas representando as parcelas
                val contasDasParcelas = parcelas.mapNotNull { parcela ->

                    val contaPai = contasMapeadas[parcela.idContaPai]
                        ?: contaRepository.getContaById(parcela.idContaPai)

                    contaPai?.let {
                        LinhaDoTempoModel(
                            id = parcela.id.toLong(),
                            name = "${it.name} - Parcela ${parcela.numeroParcela}/${parcela.totalParcelas}",
                            category = it.categoria,
                            subCategory = it.subCategoria,
                            valor = parcela.valor,
                            icon = it.icon,
                            colorIcon = it.colorIcon,
                            colorCard = it.colorCard,
                            dateTime = parcela.data.toLocalDate().atStartOfDay(),
                        )
                    }
                }

                //Junta as contas normais(sem as pai) + as contas das parcelas
                val todasAsContas = transformaContaEmLinhaDoMesViewModel + contasDasParcelas

                todasAsContas
                    .sortedBy { it.dateTime }
                    .groupBy { it.dateTime.toLocalDate() }
                    .mapNotNull { (data, contasDoDia) ->
                        val contasOrdenadas = contasDoDia.sortedByDescending { it.dateTime }
                        val primeira = contasOrdenadas.first()
                        val outras = contasOrdenadas.drop(1)

                        HistoricoDoDia(
                            data = data,
                            primaryTimeline = primeira,
                            otherTimeline = outras
                        )
                    }
                    .sortedByDescending { it.data } //Pega da mais recente a mais antiga
            }
        }
       //Fica on quando a UI estiver ativa e tiver um observador, inicia com lista vazia
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())  //StateFlow que se inicia com o ViewModel e dura quando o ViewModel durar

    //Pega as primeiras três letras do mês
    fun setData(mes: String){
        _data.value = mes
    }
}
