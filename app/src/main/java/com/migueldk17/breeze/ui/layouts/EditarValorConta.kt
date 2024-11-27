package com.migueldk17.breeze.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.animation.ColorTransitionFromCenter
import com.migueldk17.breeze.viewmodels.BreezeViewModel

@Composable
fun EditarValorConta(viewModel: BreezeViewModel, modifier: Modifier = Modifier){
    val cardColor = viewModel.cardColor.collectAsState().value
    Log.d(TAG, "EditarValorConta: $cardColor")
    val iconColor = viewModel.iconColor.collectAsState()
    var text by remember {
        mutableStateOf("")
    }
    Box {
        ColorTransitionFromCenter(cardColor)

    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(text, onValueChange = { value ->
                text = value
            },
                placeholder = {
                    Text("Valor")
                },
                modifier = Modifier.size(width = 110.dp, height = 70.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.width(10.dp))
            OutlinedIconButton(onClick = {}) {
                Icon(Icons.Outlined.Check, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditarValorContaPreview(/*navController: NavController*/baseColor: Color){
    Log.d(TAG, "EditarValorConta: $baseColor")
    var text by remember {
        mutableStateOf("")
    }
    Box {
        ColorTransitionFromCenter(baseColor)

    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(text, onValueChange = { value ->
                text = value
            },
                placeholder = {
                    Text("Valor")
                },
                modifier = Modifier.size(width = 110.dp, height = 70.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.width(10.dp))
            OutlinedIconButton(onClick = {}) {
                Icon(Icons.Outlined.Check, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(150.dp))
    }


}
@Composable
@Preview
private fun Preview(){
    EditarValorContaPreview(Color.Yellow)
}