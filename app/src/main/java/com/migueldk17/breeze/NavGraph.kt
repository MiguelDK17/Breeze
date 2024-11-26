package com.migueldk17.breeze

import androidx.compose.ui.graphics.Color


sealed class Screen(val route: String){
    data object PaginaInicial: Screen("pagina_inicial")
    data object Historico: Screen("historico")
    data object Configuracoes: Screen("configuracoes")
    data object EditarValorConta: Screen("editar_valor_conta")
}