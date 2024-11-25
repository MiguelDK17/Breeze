package com.migueldk17.breeze


sealed class Screen(val route: String){
    data object PaginaInicial: Screen("pagina_inicial")
    data object Historico: Screen("historico")
    data object Configuracoes: Screen("configuracoes")
    data object WaveScreen: Screen("WaveScreen")
}