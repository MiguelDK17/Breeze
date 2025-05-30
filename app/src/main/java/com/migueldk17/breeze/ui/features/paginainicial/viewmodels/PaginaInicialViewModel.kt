package com.migueldk17.breeze.ui.features.paginainicial.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.entity.Receita
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.repository.ReceitaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PaginaInicialViewModel @Inject constructor(
    private val receitaRepository: ReceitaRepository,
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository
): ViewModel() {
    //Banco de dados
    private val _receita = MutableStateFlow<Double?>(null)
    val receita: StateFlow<Double?> = _receita.asStateFlow()

    //Variavwl que controla o estado de carregamente em PaginaInicial
    val carregando = MutableStateFlow(true)

    private val _conta = MutableStateFlow<List<Conta>>(emptyList())
    val conta: StateFlow<List<Conta>> = _conta

    private val _contaSelecionada = MutableStateFlow<Conta?>(null)
    val contaSelecionada: StateFlow<Conta?> = _contaSelecionada.asStateFlow()

    private val _showBottomSheet = MutableStateFlow<Boolean>(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()



    init {
        obterReceita()

        obterContas()
    }
    //Pega a receita
    private fun obterReceita() {
        viewModelScope.launch {
            receitaRepository.getSaldoTotal().collect { receita ->
                _receita.value = receita ?: 0.00 //Valor inicial
            }
        }
    }
    //Pega todas as contas cadastradas no app
    private fun obterContas() {
        viewModelScope.launch {
            contaRepository.getContas()
                .collectLatest { lista ->
                    //Manda a lista de contas pra variavel _conta
                    _conta.value = lista

                    delay(500) //Adiciona um pequeno delay

                    carregando.value = false //Muda o valor para false, indicando que o carregamento terminou
                }
        }
    }
    fun atualizaBottomSheet(boolean: Boolean){
        _showBottomSheet.value = boolean
    }


    //Atualiza o saldo do usuário
    fun adicionaReceita(valor: Double, descricao: String, data: LocalDate) {
        viewModelScope.launch {
            val receita = Receita(
                valor = valor / 100,
                descricao = descricao,
                data = data.toString()
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
    //Pega todas as parcelas baseadas no ID da conta pai
     fun pegaParcelasDaConta(idContaPai: Long): Flow<List<ParcelaEntity>>{
        return parcelaRepository.buscaParcelas(idContaPai)

    }

}