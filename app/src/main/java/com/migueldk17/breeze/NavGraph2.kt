package com.migueldk17.breeze

//Rotas em string que estão sendo usadas pelo Jetpack Navigation
sealed class NavGraph2(val route: String){
    data object Passo1: NavGraph2("passo1")
    data object Passo2: NavGraph2("passo2")
    data object Passo3: NavGraph2("passo3")
    data object Passo4: NavGraph2("passo4")
    data object Passo5: NavGraph2("passo5")
    data object Final: NavGraph2("final")

}