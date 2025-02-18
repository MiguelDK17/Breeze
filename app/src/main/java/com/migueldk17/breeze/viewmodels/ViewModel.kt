package com.migueldk17.breeze.viewmodels


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.SaldoDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.Saldo
import com.migueldk17.breeze.converters.toDatabaseValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreezeViewModel @Inject constructor(
    private val saldoDao: SaldoDao,
    private val contaDao: ContaDao
): ViewModel() {
    //Banco de dados
    private val _saldo = MutableStateFlow<Saldo?>(null)
    val saldo: StateFlow<Saldo?> get() = _saldo

    val conta: StateFlow<List<Conta>> = contaDao.getConta()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    private val _arrayColor = MutableStateFlow(intArrayOf())
    //val arrayColor: StateFlow<IntArray> = _arrayColor.asStateFlow()

    private val _cardColor = MutableStateFlow(Color.Unspecified)
    val cardColor: StateFlow<Color> = _cardColor.asStateFlow()

    private val _iconColor = MutableStateFlow(Color.Unspecified)
    val iconColor: StateFlow<Color> = _iconColor.asStateFlow()

    private val _nomeConta = MutableStateFlow("")
    val nomeConta: StateFlow<String> = _nomeConta.asStateFlow()

    
    private val _iconeCardConta = MutableStateFlow(BreezeIcons.Unspecified.IconUnspecified)
    val iconeCardConta: StateFlow<BreezeIconsType> get() = _iconeCardConta.asStateFlow()

    private val _corIcone = MutableStateFlow(Color.Unspecified)
    val corIcone: StateFlow<Color> get() = _corIcone.asStateFlow()

    private val _corCard = MutableStateFlow(Color.Unspecified)
    val corCard: StateFlow<Color> get() = _corCard.asStateFlow()

    private val _valorConta = MutableStateFlow(1.0)
    val valorConta: StateFlow<Double> get() = _valorConta.asStateFlow()


    init {
        viewModelScope.launch {
            _saldo.value = saldoDao.getSaldo() ?: Saldo(valor = 0.00) //Valor inicial
        }
    }


    //Transforma a cor de int para Color para manipulação
    fun transformaCor(array: IntArray){
        _arrayColor.value = array
        //Pega o primeiro valor do array, isto é, a cor do card
        val colorCard = _arrayColor.value[0]
        //Pega o segundo valor do array, isto é, a cor do icon
        val colorIcon = _arrayColor.value[1]

        val transformaCorCard = Color(colorCard)
        _cardColor.value = transformaCorCard
        val transformaCorIcon = Color(colorIcon)
        _iconColor.value = transformaCorIcon
    }

    fun setNomeConta(string: String){
        _nomeConta.value = string
    }
    //Atualiza o saldo do usuário
    fun atualizaSaldo(double: Double){
        viewModelScope.launch {
            if (saldoDao.getSaldo() == null) {
                val saldoAtual = Saldo(id = 0, valor = double / 100)
                saldoDao.inserirSaldo(saldoAtual)
                Log.d(TAG, "atualizaSaldo: ${saldoDao.getSaldo()}")
                _saldo.value = saldoAtual
            } else {
                Log.d(TAG, "atualizaSaldo: Caiu no update")
                val saldoAtualizado = Saldo(id = 0, valor = double / 100)
                saldoDao.atualizarSaldo(saldoAtualizado)
                Log.d(TAG, "atualizaSaldo: ${saldoDao.getSaldo()}")
                _saldo.value = saldoAtualizado
            }
        }
    }

    fun guardaIconCard(icon: BreezeIconsType){
        _iconeCardConta.value = icon
        if (iconeCardConta.value != BreezeIcons.Unspecified.IconUnspecified) {
            Log.d(TAG, "guardaIconEscolhido: icone selecionado")
        }
    }

    fun guardaCorIconeEscolhida(icon: BreezeIconsType){
        _corIcone.value = icon.color
        Log.d(TAG, "guardaIconEscolhido: icone selecionado")
    }
    fun guardaCorIconePadrao(color: Color){
        _corIcone.value = color
        Log.d(TAG, "guardaCorIconePadrao: cor padrão para icone escolhida")
    }
    fun guardaIconCorCardEscolhida(icon: BreezeIconsType){
        _corCard.value = icon.color
        Log.d(TAG, "guardaIconEscolhido: icone selecionado")
    }
    fun guardaCorCardPadrao(color: Color){
        _corCard.value = color
    }
    fun guardaValorConta(valor: Double){
        _valorConta.value = valor/100
    }


    fun salvaContaDatabase(){
        viewModelScope.launch {
            val name = _nomeConta.value
            val valor = _valorConta.value
            val icon = _iconeCardConta.value.enum.toDatabaseValue()
            val colorIcon = _corIcone.value.toDatabaseValue()
            val colorCard = _corCard.value.toDatabaseValue()

            val conta = Conta(
                name = name,
                valor = valor,
                icon = icon,
                colorIcon = colorIcon,
                colorCard = colorCard
            )
            contaDao.insertConta(conta)
            Log.d(TAG, "salvaContaDatabase: ${contaDao.getConta()}")

        }
    }
}