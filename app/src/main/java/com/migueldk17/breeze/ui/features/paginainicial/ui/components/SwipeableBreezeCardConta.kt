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
    onDetalhes: () -> Unit,
    onExcluir: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onDetalhes()
                    false
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onExcluir()
                    false
                }
                else -> false
            }
        }
    )
    val progress = state.progress

    val currentOnDetalhes by rememberUpdatedState(onDetalhes)
    val currentOnExcluir by rememberUpdatedState(onExcluir)

    
    LaunchedEffect(state.progress) {
        Log.d(TAG, "SwipeableBreezeCardConta: O progresso: ${state.progress}")
        Log.d(TAG, "SwipeableBreezeCardConta: O settledValue: ${ state.settledValue}")
        if (progress >= 1.0.toFloat()) {
            state.reset()
        }
    }


//    LaunchedEffect(state.currentValue) {
//        when (state.currentValue) {
//            SwipeToDismissBoxValue.StartToEnd -> {
//                currentOnDetalhes()
//                Log.d(TAG, "SwipeableBreezeCardConta: teste")
//                state.reset()
//            }
//
//            SwipeToDismissBoxValue.EndToStart -> {
//                currentOnExcluir()
//                state.reset()
//            }
//            else -> Unit
//        }
//    }

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