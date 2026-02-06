package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SwipeableBreezeCardConta(
    onDetalhes: (Boolean) -> Unit,
    onExcluir: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onDetalhes(true)
                    false
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onExcluir(true)
                    false
                }
                else -> false
            }
        }
    )

    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            SwipeBackground(
                dismissState = state,
            )
        },
        modifier = modifier,
    ) {
        content()
    }

}