package com.migueldk17.breeze.ui.components


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.theme.BreezeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezeTopAppBar(){
    MediumTopAppBar(
        title = {
            Text("Bem Vindo !",
                fontSize = 25.sp,
                style = MaterialTheme.typography.titleLarge)
        })
}

@Composable
@Preview
private fun Preview(){
    BreezeTheme {
        BreezeTopAppBar()
    }

}
