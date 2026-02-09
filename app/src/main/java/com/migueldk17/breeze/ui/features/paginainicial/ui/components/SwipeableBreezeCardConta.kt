package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import android.util.Log
import android.content.ContentValues.TAG
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
    onDetalhes: (Boolean) -> Unit,
    onExcluir: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    val state = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        positionalThreshold = { distance ->
            distance * 0.60f //30% de swipe
        }
    )
    val currentOnDetalhes by rememberUpdatedState(onDetalhes)
    val currentOnExcluir by rememberUpdatedState(onExcluir)

    LaunchedEffect(state.currentValue) {
        when (state.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                currentOnDetalhes(true)
                state.snapTo(SwipeToDismissBoxValue.Settled)
            }

            SwipeToDismissBoxValue.EndToStart -> {
                currentOnExcluir(true)
                state.snapTo(SwipeToDismissBoxValue.Settled)
            }

            else -> Unit
        }
    }

    val progress = state.progress
    val alpha = progress.coerceIn(0f, 1f)
    Log.d(TAG, "SwipeableBreezeCardConta: valor de alpha $alpha")

    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            SwipeBackground(
                dismissState = state,
                alpha = alpha
            )
        },
        modifier = modifier,
    ) {
        content()
    }

}