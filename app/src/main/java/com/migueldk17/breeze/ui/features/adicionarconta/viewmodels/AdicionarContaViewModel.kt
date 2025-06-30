package com.migueldk17.breeze.ui.features.adicionarconta.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toDatabaseValue
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.repository.ContaRepository
import com.migueldk17.breeze.repository.ParcelaRepository
import com.migueldk17.breeze.ui.features.adicionarconta.models.DadosContaUI
import com.migueldk17.breeze.ui.utils.arredondarValor
import com.migueldk17.breeze.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class AdicionarContaViewModel @Inject constructor(
    private val contaRepository: ContaRepository,
    private val parcelaRepository: ParcelaRepository
): ViewModel() {

    private val _nomeConta = MutableStateFlow("")
    val nomeConta: StateFlow<String> = _nomeConta.asStateFlow()

    private val _categoriaConta = MutableStateFlow("")
    val categoriaConta: StateFlow<String> = _categoriaConta.asStateFlow()

    private val _subcategoriaConta = MutableStateFlow("")
    val subcategoriaConta: StateFlow<String> = _subcategoriaConta.asStateFlow()

    private val _isContaParcelada = MutableStateFlow(false)
    val isContaParcelada: StateFlow<Boolean> = _isContaParcelada.asStateFlow()

    private val _dataDaConta = MutableStateFlow(LocalDate.now())
    val dataDaConta: StateFlow<LocalDate> = _dataDaConta.asStateFlow()

    private val _quantidadeDeParcelas = MutableStateFlow(0)
    val quantidadeDeParcelas: StateFlow<Int> = _quantidadeDeParcelas.asStateFlow()

    private val _taxaDeJurosMensal = MutableStateFlow(0.00)
    val taxaDeJurosMensal: StateFlow<Double> = _taxaDeJurosMensal.asStateFlow()

    private val _valorDasParcelas = MutableStateFlow(1.0)
    val valorDasParcelas: StateFlow<Double> = _valorDasParcelas.asStateFlow()

    private val _iconeCardConta = MutableStateFlow(BreezeIcons.Unspecified.IconUnspecified)
    val iconeCardConta: StateFlow<BreezeIconsType> get() = _iconeCardConta.asStateFlow()

    private val _corIcone = MutableStateFlow(Color.Unspecified)
    val corIcone: StateFlow<Color> get() = _corIcone.asStateFlow()

    private val _corCard = MutableStateFlow(Color.Unspecified)
    val corCard: StateFlow<Color> get() = _corCard.asStateFlow()

    private val _valorConta = MutableStateFlow(1.0)
    val valorConta: StateFlow<Double> get() = _valorConta.asStateFlow()

    private val _salvarContasState = MutableStateFlow<UiState<Unit>>(UiState.Loading)

    private val _salvarParcelasState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val salvarParcelasState: StateFlow<UiState<Unit>> = _salvarParcelasState.asStateFlow()

    val dadosContaUI: StateFlow<DadosContaUI> = combine(
        listOf(
            nomeConta,
            iconeCardConta,
            corIcone,
            corCard,
            valorConta,
            categoriaConta,
            subcategoriaConta,
            valorDasParcelas,
            quantidadeDeParcelas,
            dataDaConta,
            isContaParcelada,
            taxaDeJurosMensal
        )
    ) { valores ->
        DadosContaUI(
            nome = valores[0] as String,
            icone = valores[1] as BreezeIconsType,
            corIcone = valores[2] as Color,
            corCard = valores[3] as Color,
            valor = valores[4] as Double,
            categoria = valores[5] as String,
            subCategoria = valores[6] as String,
            valorParcela = valores[7] as Double,
            totalParcelas = valores[8] as Int,
            data = valores[9] as LocalDate,
            isParcelada = valores[10] as Boolean,
            taxaJuros = valores[11] as Double
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DadosContaUI(
        nome = "",
        icone = BreezeIcons.Unspecified.IconUnspecified,
        corIcone = Color.Unspecified,
        corCard = Color.Unspecified,
        valor = 0.0,
        categoria = "",
        subCategoria = "",
        valorParcela = 0.0,
        totalParcelas = 0,
        data = LocalDate.now(),
        isParcelada = false,
        taxaJuros = 0.0
    ))

    init {
        viewModelScope.launch {
            salvarParcelasState.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> Log.d(TAG, "UI_STATE: Carregando dados")
                    is UiState.Success<*> -> Log.d(TAG, "UI_STATE: Dados carregados com sucesso!")
                    is UiState.Error -> Log.d(TAG, "UI_STATE: Erro ao carregar dados")
                    is UiState.Empty -> Log.d(TAG, "UI_STATE: Lista vazia")
                }
            }
        }
    }



    fun setNomeConta(string: String) {
        _nomeConta.value = string
    }
    fun setCategoria(string: String) {
        _categoriaConta.value = string
    }
    fun setSubcategoria(text: String){
        _subcategoriaConta.value = text
    }

    //Guarda o icone que será usado no card de conta no ViewModel
    fun guardaIconCard(icon: BreezeIconsType) {
        _iconeCardConta.value = icon
    }

    //Guarda a cor do Icone do Card de Conta
    fun guardaCorIconeEscolhida(icon: BreezeIconsType) {
        _corIcone.value = icon.color
    }
    //Guarda a cor padrão(Surface) do aplicativo para ser usada no icone do card de contas
    fun guardaCorIconePadrao(color: Color) {
        _corIcone.value = color
    }
    //Guarda a cor do Card de Contas
    fun guardaCorCardEscolhida(icon: BreezeIconsType) {
        _corCard.value = icon.color
    }
    //Guarda a cor padrão do aplicativo(Surface) para ser usada no card de contas
    fun guardaCorCardPadrao(color: Color) {
        _corCard.value = color
    }
    //Guarda o valor da conta
    fun guardaValorConta(valor: Double) {
        _valorConta.value = valor / 100
    }

    fun guardaIsContaParcelada(boolean: Boolean){
        _isContaParcelada.value = boolean
    }

    //Guarda a data da primeira parcela da conta
    fun guardaDataConta(data: LocalDate){
        _dataDaConta.value = data
    }

    fun guardaPorcentagemJuros(string: String){
        val valor = string.toDoubleOrNull()?.div(100) ?: 0.0
        _taxaDeJurosMensal.value = valor
        Log.d(TAG, "guardaPorcentagemJuros: ${_taxaDeJurosMensal.value}")
    }

    //Guarda a quantidade de parcelas
    fun guardaQtdParcelas(string: String){
        val valor = string.filter { it.isDigit() }.toInt()
        _quantidadeDeParcelas.value = valor

        guardaValorDasParcelas()
    }

    private fun guardaValorDasParcelas(){
        _valorDasParcelas.value = guardaValorDaParcela()
    }

    private fun guardaValorDaParcela(): Double{
        return if (_taxaDeJurosMensal.value == 0.00) calculaParcelasSemJuros() else calculaParcelasComJuros()
    }

    private fun calculaParcelasSemJuros(): Double{
        Log.d(TAG, "calculaParcelasSemJuros: Parcelas sem juros acionada")
        return if (_quantidadeDeParcelas.value > 0){
            _valorConta.value / quantidadeDeParcelas.value
        }
        else {
            0.0
        }
    }

    private fun calculaParcelasComJuros(): Double {
        Log.d(TAG, "calculaParcelasComJuros: Parcelas com juros acionada")
        if (_quantidadeDeParcelas.value <= 0 || _taxaDeJurosMensal.value < 0) return 0.0

        val i = _taxaDeJurosMensal.value
        val n = _quantidadeDeParcelas.value

        return (_valorConta.value * i) / (1 - (1 + i).pow(-n))
    }



    //Guarda a Conta no Room
    fun salvaContaDatabase() {
        viewModelScope.launch {
            _salvarContasState.value = UiState.Loading
            val name = _nomeConta.value
            val categoria = _categoriaConta.value
            val subCategoria = _subcategoriaConta.value
            val valor = _valorConta.value
            val icon = _iconeCardConta.value.enum.toDatabaseValue()
            val colorIcon = _corIcone.value.toDatabaseValue()
            val colorCard = _corCard.value.toDatabaseValue()
            val dateTime = LocalDateTime.now().toDatabaseValue()
            val isContaParcelada = _isContaParcelada.value

            val conta = Conta(
                name = name,
                categoria = categoria,
                subCategoria = subCategoria,
                valor = valor,
                icon = icon,
                colorIcon = colorIcon,
                colorCard = colorCard,
                dateTime = dateTime,
                isContaParcelada = isContaParcelada
            )
            try {
                val idContaPai = contaRepository.adicionarConta(conta)

                if (isContaParcelada) salvaParcelasDatabase(idContaPai)

                _salvarContasState.value = UiState.Success(Unit)
            } catch (e: Exception) {
                _salvarContasState.value = UiState.Error("Erro ao salvar: ${e.message}")
            }

        }
    }

    fun salvaParcelasDatabase(idContaPai: Long){
            viewModelScope.launch {
                _salvarContasState.value = UiState.Loading
                Log.d(TAG, "salvaParcelasDatabase: valor das parcelas sem formatação: ${_valorDasParcelas.value}")

                val idContaPai = idContaPai
                val valor = arredondarValor(_valorDasParcelas.value)
                val porcentagemJuros = _taxaDeJurosMensal.value
                val totalParcelas = _quantidadeDeParcelas.value
                val dataInicial = _dataDaConta.value

                val listaParcelas = mutableListOf<ParcelaEntity>()

                for (i in 1..totalParcelas) {
                    val dataParcela = dataInicial.plusMonths(((i - 1).toLong())).toDatabaseValue()


                    val parcela = ParcelaEntity(
                        idContaPai = idContaPai,
                        valor = valor,
                        porcentagemJuros = porcentagemJuros,
                        numeroParcela = i,
                        totalParcelas = totalParcelas,
                        data = dataParcela
                    )

                    listaParcelas.add(parcela)
                }
                 try {
                     parcelaRepository.adicionaParcelas(listaParcelas)
                     Log.d(TAG, "salvaParcelasDatabase: $listaParcelas")
                     _salvarParcelasState.value = UiState.Success(Unit)
                 } catch (e: Exception){
                     _salvarParcelasState.value = UiState.Error(e.message ?: "Erro desconhecido")
                 }

            }

    }


}