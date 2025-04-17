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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class BreezeViewModel @Inject constructor(
    private val saldoDao: SaldoDao,
    private val contaDao: ContaDao
): ViewModel() {
    //Banco de dados
    private val _saldo = MutableStateFlow<Saldo?>(null)
    val saldo: StateFlow<Saldo?> get() = _saldo

    //Variavwl que controla o estado de carregamente em PaginaInicial
    val carregando = MutableStateFlow(true)

    private val _conta = MutableStateFlow<List<Conta>>(emptyList())
    val conta: StateFlow<List<Conta>> = _conta



    private val _contaSelecionada = MutableStateFlow<Conta?>(null)
    val contaSelecionada: StateFlow<Conta?> = _contaSelecionada.asStateFlow()



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

            //Pega todas as contas registradas no Room
            contaDao.getContas()
                .collectLatest { lista ->
                    //Manda a lista de contas pra variavel _conta
                    _conta.value = lista

                    delay(500) //Adiciona um pequeno delay

                    carregando.value = false //Muda o valor para false, indicando que o carregamento terminou
                }
        }
    }

    fun setNomeConta(string: String) {
        _nomeConta.value = string
    }

    //Atualiza o saldo do usuário
    fun atualizaSaldo(double: Double) {
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

    //Guarda o icone que será usado no card de conta no ViewModel
    fun guardaIconCard(icon: BreezeIconsType) {
        _iconeCardConta.value = icon
        if (iconeCardConta.value != BreezeIcons.Unspecified.IconUnspecified) {
            Log.d(TAG, "guardaIconEscolhido: icone selecionado")
        }
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
        Log.d(TAG, "guardaIconEscolhido: icone selecionado")
    }
    //Guarda a cor padrão do aplicativo(Surface) para ser usada no card de contas
    fun guardaCorCardPadrao(color: Color) {
        _corCard.value = color
    }
    //Guarda o valor da conta
    fun guardaValorConta(valor: Double) {
        _valorConta.value = valor / 100
    }

    //Guarda a Conta no Room
    fun salvaContaDatabase() {
        viewModelScope.launch {
            val name = _nomeConta.value
            val valor = _valorConta.value
            val icon = _iconeCardConta.value.enum.toDatabaseValue()
            val colorIcon = _corIcone.value.toDatabaseValue()
            val colorCard = _corCard.value.toDatabaseValue()
            val dateTime = LocalDateTime.now().toDatabaseValue()

            val conta = Conta(
                name = name,
                valor = valor,
                icon = icon,
                colorIcon = colorIcon,
                colorCard = colorCard,
                dateTime = dateTime
            )
            contaDao.insertConta(conta)
        }
    }

    //Pega as informações da conta selecionada em PaginaInicial baseada no ID fornecido
    fun pegaContaSelecionada(id: Int){
        viewModelScope.launch {
            _contaSelecionada.value = contaDao.getContaById(id)
        }
    }
    //Apaga a conta selecionada
    fun apagaConta(conta: Conta) {
        viewModelScope.launch {
            contaDao.apagarConta(conta)
        }
    }

}