package com.migueldk17.breeze


//Rotas em string que estão sendo usadas pelo Jetpack Navigation
sealed class Screen(val route: String){
    data object PaginaInicial: Screen("pagina_inicial")
    data object Historico: Screen("historico")
    data object Configuracoes: Screen("configuracoes")
}