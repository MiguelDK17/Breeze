package com.migueldk17.breeze.viewmodels


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.migueldk17.breeze.dao.SaldoDao
import com.migueldk17.breeze.entity.Saldo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreezeViewModel @Inject constructor(
    private val saldoDao: SaldoDao
): ViewModel() {
    private val _arrayColor = MutableStateFlow(intArrayOf())
    //val arrayColor: StateFlow<IntArray> = _arrayColor.asStateFlow()

    private val _cardColor = MutableStateFlow(Color.Unspecified)
    val cardColor: StateFlow<Color> = _cardColor.asStateFlow()

    private val _iconColor = MutableStateFlow(Color.Unspecified)
    val iconColor: StateFlow<Color> = _iconColor.asStateFlow()

    private val _nomeConta = MutableStateFlow("")
    val nomeConta: StateFlow<String> = _nomeConta.asStateFlow()

    private val _saldo = MutableStateFlow<Saldo?>(null)
    val saldo: StateFlow<Saldo?> get() = _saldo

    init {
        viewModelScope.launch {
            _saldo.value = saldoDao.getSaldo() ?: Saldo(valor = 3000.00) //Valor inicial
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

    fun setNome(string: String){
        _nomeConta.value = string
    }
    fun atualizaSaldo(double: Double){
        viewModelScope.launch {
            val saldoAtual = Saldo(valor = double)
            saldoDao.inserirSaldo(saldoAtual)
            _saldo.value = saldoAtual
        }
    }

}