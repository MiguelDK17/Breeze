package com.migueldk17.breeze.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BreezeViewModel: ViewModel() {
    private val _selectedColor = MutableStateFlow(Color.Transparent)
    val selectedColor: StateFlow<Color> = _selectedColor.asStateFlow()

    fun salvaCor(color: Color){
        _selectedColor.value = color
    }
}