package com.migueldk17.breeze.viewmodels


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BreezeViewModel: ViewModel() {
    private val _arrayColor = MutableStateFlow(intArrayOf())
    //val arrayColor: StateFlow<IntArray> = _arrayColor.asStateFlow()

    private val _cardColor = MutableStateFlow(Color.Unspecified)
    val cardColor: StateFlow<Color> = _cardColor.asStateFlow()

    private val _iconColor = MutableStateFlow(Color.Unspecified)
    val iconColor: StateFlow<Color> = _iconColor.asStateFlow()

    private val _nomeConta = MutableStateFlow("")
    val nomeConta: StateFlow<String> = _nomeConta.asStateFlow()

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
}