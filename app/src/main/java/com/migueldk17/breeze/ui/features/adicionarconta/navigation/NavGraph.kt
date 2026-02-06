package com.migueldk17.breeze.ui.features.adicionarconta.navigation

//Rotas em string que estão sendo usadas pelo Jetpack Navigation
sealed class NavGraph(val route: String){
    data object Passo1: NavGraph("passo1")
    data object Passo2: NavGraph("passo2")
    data object Passo3: NavGraph("passo3")
    data object Passo4: NavGraph("passo4")
    data object Passo5: NavGraph("passo5")
    data object Final: NavGraph("final")

}