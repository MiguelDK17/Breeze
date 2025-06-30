package com.migueldk17.breeze.ui.features.historico.ui.viewmodels


import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.historico.model.HistoricoDoDia
import com.migueldk17.breeze.ui.utils.formataMesAno
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
    private val _contasPorMes = MutableStateFlow<List<Conta>>(emptyList())
    val contasPorMes: StateFlow<List<Conta>> = _contasPorMes.asStateFlow()

    private val _parcela: MutableStateFlow<UiState<ParcelaEntity>> = MutableStateFlow(UiState.Loading)
    val parcela: StateFlow<UiState<ParcelaEntity>> = _parcela.asStateFlow()

    init {
        observarContaPorMes()
    }

    private fun observarContaPorMes() {
        viewModelScope.launch {
            _data.collectLatest { mes ->
                val dataFormatadaParaParcela = formataMesAno(LocalDate.now()) + "%"
                Log.d(TAG, "observarContaPorMes: $dataFormatadaParaParcela")

                val contasFlow = contaRepository.getContasPorMes(mes.take(3))
                val parcelasFlow = parcelaRepository.buscaTodasAsParcelasDoMes(dataFormatadaParaParcela)

                combine(contasFlow, parcelasFlow) { contas, parcelas ->
                    val contasMapeadas = contas.associateBy { it.id }
                    val idsContaPai = parcelas.map { it.idContaPai }.toSet()
                    val contasFiltradas = contas.filterNot { it.id in idsContaPai }

                    val contasDasParcelas = parcelas.mapNotNull { parcela ->
                        contasMapeadas[parcela.idContaPai]?.let { contaPai ->
                            Conta(
                                id = parcela.id.toLong(),
                                name = "${contaPai.name} - Parcela ${parcela.numeroParcela}/${parcela.totalParcelas}",
                                categoria = contaPai.categoria,
                                subCategoria = contaPai.subCategoria,
                                valor = parcela.valor,
                                icon = contaPai.icon,
                                colorIcon = contaPai.colorIcon,
                                colorCard = contaPai.colorCard,
                                dateTime = parcela.data.toLocalDate().atStartOfDay().toString(),
                                isContaParcelada = true
                            )
                        }
                    }
                    val todasAsContas = contasFiltradas + contasDasParcelas
                    todasAsContas.sortedBy { it.dateTime }
                }.collectLatest { contasOrdenadas ->
                    _contasPorMes.value = contasOrdenadas
                }
            }
        }
    }

    //Pega as contas do mes sem filtro
    fun buscaContasPorMes(): StateFlow<List<Conta>>{
        val contasFiltradas = _data.flatMapLatest { mes ->
                //Data formatada para consulta no Room para Parcelas
                val dataFormadadaParaParcela = formataMesAno(LocalDate.now()) + "%"
                //Flow de Lista de Contas
                val contasFlow = contaRepository.getContasPorMes(mes.take(3))
                //Flow de Lista de Contas
                val parcelasFlow = parcelaRepository.buscaTodasAsParcelasDoMes(dataFormadadaParaParcela)

                //Combine junta os fluxos de contas e parcelas
                combine(contasFlow, parcelasFlow) { contas, parcelas ->
                    //Mapeia as contas baseado no id
                    val contasMapeadas = contas.associateBy {it.id}

                    //Cria um Set com os IDs das contas que são pais de parcelas no mês
                    val idsContaPai = parcelas.map { it.idContaPai }.toSet()

                    //Filtra as contas normais, removendo as contas que são pai de parcela
                    val contasFiltradas = contas.filterNot { it.id in idsContaPai }

                    //Agora monta as contas representando as parcelas
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

                    //Junta as contas normais(sem as pai) + as contas das parcelas
                    val todasAsContas = contasFiltradas + contasDasParcelas
                    val contasFiltradasPorData = todasAsContas
                        .sortedBy { it.dateTime } //Pega da mais recente a mais antiga
                    contasFiltradasPorData

                }
            }
            //Fica on quando a UI estiver ativa e tiver um observador, inicia com lista vazia
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                emptyList())  //StateFlow que se inicia com o ViewModel e dura quando o ViewModel durar

        return contasFiltradas

    }

     fun buscaParcelaPorId(idParcela: Long): UiState<ParcelaEntity> {
         Log.d(TAG, "buscaParcelaPorId: $idParcela")
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
            //Data formatada para consulta no Room para Parcelas
            val dataFormadadaParaParcela = formataMesAno(LocalDate.now()) + "%"
            //Flow de Lista de Contas
            val contasFlow = contaRepository.getContasPorMes(mes.take(3))
            //Flow de Lista de Contas
            val parcelasFlow = parcelaRepository.buscaTodasAsParcelasDoMes(dataFormadadaParaParcela)

            //Combine junta os fluxos de contas e parcelas
            combine(contasFlow, parcelasFlow) { contas, parcelas ->
                //Mapeia as contas baseado no id
                val contasMapeadas = contas.associateBy {it.id}

                //Cria um Set com os IDs das contas que são pais de parcelas no mês
                val idsContaPai = parcelas.map { it.idContaPai }.toSet()

                //Filtra as contas normais, removendo as contas que são pai de parcela
                val contasFiltradas = contas.filterNot { it.id in idsContaPai }

                //Agora monta as contas representando as parcelas
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

                //Junta as contas normais(sem as pai) + as contas das parcelas
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
                    .sortedByDescending { it.data } //Pega da mais recente a mais antiga
            }
        }
       //Fica on quando a UI estiver ativa e tiver um observador, inicia com lista vazia
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())  //StateFlow que se inicia com o ViewModel e dura quando o ViewModel durar

    //Pega as primeiras três letras do mês
    fun setData(mes: String){
        _data.value = mes.take(3)
    }
}
