package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier

@Composable
fun SwipeableBreezeCardConta(
    onDetalhes: () -> Unit,
    onExcluir: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    val dismissState = rememberSwipeToDismissBoxState()

    val currentOnDetalhes by rememberUpdatedState(onDetalhes)
    val currentOnExcluir by rememberUpdatedState(onExcluir)


    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                currentOnDetalhes()
                dismissState.reset()
            }

            SwipeToDismissBoxValue.EndToStart -> {
                currentOnExcluir()
                dismissState.reset()
            }
            else -> Unit
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            SwipeBackground(
                dismissState = dismissState,
            )
        },
        modifier = modifier
    ) {
        content()
    }

}