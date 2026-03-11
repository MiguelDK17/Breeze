package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun PassoColumnPai(
    modifier: Modifier = Modifier,
    composable: @Composable () -> Unit
){
    Column(
        modifier = modifier
    ) {
        composable
    }
}