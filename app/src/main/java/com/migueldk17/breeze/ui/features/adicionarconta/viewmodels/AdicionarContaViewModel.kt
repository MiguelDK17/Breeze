package com.migueldk17.breeze.ui.features.adicionarconta.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toDatabaseValue
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.entity.Conta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AdicionarContaViewModel @Inject constructor(
    private val contaDao: ContaDao
): ViewModel() {

    private val _nomeConta = MutableStateFlow("")
    val nomeConta: StateFlow<String> = _nomeConta.asStateFlow()

    private val _categoriaConta = MutableStateFlow("")
    val categoriaConta: StateFlow<String> = _categoriaConta.asStateFlow()

    private val _subcategoriaConta = MutableStateFlow("")
    val subcategoriaConta: StateFlow<String> = _subcategoriaConta.asStateFlow()

    private val _iconeCardConta = MutableStateFlow(BreezeIcons.Unspecified.IconUnspecified)
    val iconeCardConta: StateFlow<BreezeIconsType> get() = _iconeCardConta.asStateFlow()

    private val _corIcone = MutableStateFlow(Color.Unspecified)
    val corIcone: StateFlow<Color> get() = _corIcone.asStateFlow()

    private val _corCard = MutableStateFlow(Color.Unspecified)
    val corCard: StateFlow<Color> get() = _corCard.asStateFlow()

    private val _valorConta = MutableStateFlow(1.0)
    val valorConta: StateFlow<Double> get() = _valorConta.asStateFlow()

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


}