package com.migueldk17.breeze.ui.features.paginainicial.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.entity.Receita
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.repository.ReceitaRepository
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.utils.ToastManager
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PaginaInicialViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val receitaRepository: ReceitaRepository,
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository,
): ViewModel() {
    //Banco de dados
    private val _receita = MutableStateFlow<Double?>(null)
    val receita: StateFlow<Double?> = _receita.asStateFlow()

    //Variavwl que controla o estado de carregamente em PaginaInicial
    val carregando = MutableStateFlow(true)

    private val _contaState = MutableStateFlow<UiState<List<Conta>>>(UiState.Loading)
    val contaState: StateFlow<UiState<List<Conta>>> = _contaState.asStateFlow()

    private val _receitaState = MutableStateFlow<UiState<List<Receita>>>(UiState.Loading)
    val receitaState: StateFlow<UiState<List<Receita>>> = _receitaState.asStateFlow()

    private val _conta = MutableStateFlow<List<Conta>>(emptyList())
    val conta: StateFlow<List<Conta>> = _conta


    private val _contaSelecionada = MutableStateFlow<Conta?>(null)
    val contaSelecionada: StateFlow<Conta?> = _contaSelecionada.asStateFlow()

    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    private val _parcelaDoMes = MutableStateFlow<UiState<ParcelaEntity>>(UiState.Loading)
    val parcelaState: StateFlow<UiState<ParcelaEntity>> = _parcelaDoMes.asStateFlow()

    private val _parcelasPorConta = mutableMapOf<Long, StateFlow<UiState<ParcelaEntity?>>>()


    init {
        obterReceita()

        obterContas()

        obterListaReceitas()
    }

    //Pega a receita
    private fun obterReceita() {
        viewModelScope.launch {
            receitaRepository.getSaldoTotal().collect { receita ->
                _receita.value = receita ?: 0.00 //Valor inicial
            }
        }
    }
    private fun obterListaReceitas(){
        viewModelScope.launch {
            receitaRepository.getTodasAsReceitas()
                .catch { e ->
                    _receitaState.value = UiState.Error(e.message ?: "Erro desconhecido")
                }
                .collectLatest { lista ->
                    if (lista.isEmpty()) {
                        _receitaState.value = UiState.Empty
                    } else {
                        delay(500) //Adiciona um pequeno delay
                        _receitaState.value = UiState.Success(lista)
                    }
                }
        }
    }
    //Pega todas as contas cadastradas no app
    private fun obterContas() {
        viewModelScope.launch {
            contaRepository.getContas()
                .catch { e ->
                    _contaState.value = UiState.Error(e.message ?: "Erro desconhecido")
                }
                .collectLatest { lista ->
                    if (lista.isEmpty()) {
                        _contaState.value = UiState.Empty
                        getStatus()
                    } else {
                        delay(500) //Adiciona um pequeno delay
                        _contaState.value = UiState.Success(lista)
                    }
                }
        }
    }

    fun atualizaBottomSheet(boolean: Boolean){
        _showBottomSheet.value = boolean
    }

    fun getStatus(){
        viewModelScope.launch {
            contaRepository.getStatus()
                .catch { e ->
                    ToastManager.showToast(context, "Ocorreu o erro $e")
                }
                .collectLatest { lista ->
                    if (lista.isEmpty()){
                        ToastManager.showToast(context, "A lista de status está vazia")
                    }
                    else {
                        ToastManager.showToast(context, "A lista: $lista")
                    }

                }
        }
    }


    //Atualiza o saldo do usuário
    fun adicionaReceita(
        valor: Double,
        descricao: String,
        data: LocalDate,
        icon: String
    ) {
        viewModelScope.launch {
            val receita = Receita(
                valor = valor / 100,
                descricao = descricao,
                data = data.toString(),
                icon = icon
            )
            receitaRepository.adicionarReceita(receita)
        }
    }

    //Pega as informações da conta selecionada em PaginaInicial baseada no ID fornecido
    fun pegaContaSelecionada(id: Long){
        viewModelScope.launch {
            _contaSelecionada.value = contaRepository.getContaById(id)
        }
    }
    //Apaga a conta selecionada
     fun apagaConta(conta: Conta) {
         viewModelScope.launch {
             contaRepository.apagaConta(conta)
         }
    }
    //Apaga a receita selecionada
    fun apagaReceita(receita: Receita) {
        viewModelScope.launch(Dispatchers.IO) {
            receitaRepository.apagaReceita(receita)
        }
    }
    //Pega todas as parcelas baseadas no ID da conta pai
     fun pegaParcelasDaConta(idContaPai: Long): Flow<List<ParcelaEntity>>{
        return parcelaRepository.buscaParcelasDaConta(idContaPai)
    }
    //Função que observa as contas do mês
    fun observeParcelaDoMes(idContaPai: Long, mesAno: String): StateFlow<UiState<ParcelaEntity?>>{
        //Retorna o StateFlow com UiState
        return _parcelasPorConta.getOrPut(idContaPai) {
            //Busca as parcelas do Mêes baseado no id da conta pai + mês do ano
            parcelaRepository.buscaParcelaDoMesParaConta(idContaPai, mesAno)
                //Mapeia o resultado, caso haja resultados retorna Success com a lista, caso contrário retorna Empty
                .map { it?.let { UiState.Success(it) } ?: UiState.Empty }
                //Captura o erro
                .catch { emit(UiState.Error(it.message ?: "Erro desconhecido")) }
                //No início da chamada inicia como Loading
                .onStart { emit(UiState.Loading) }
                //Dura quando o ViewModel é criado e nunca mais para, inicia com Loading
                .stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Loading)
        }
    }

     fun apagaTodasAsParcelas(parcelasList: List<ParcelaEntity>){
         viewModelScope.launch {
             parcelaRepository.apagarTodasAsParcelas(parcelasList)
         }

    }

}