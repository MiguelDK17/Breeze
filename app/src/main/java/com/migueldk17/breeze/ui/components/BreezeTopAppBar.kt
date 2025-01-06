package com.migueldk17.breeze.ui.components


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.theme.Branco
import com.migueldk17.breeze.ui.theme.BreezeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreezeTopAppBar(){
    val context = LocalContext.current
    MediumTopAppBar(
        title = {
            Text("Bem Vindo !",
                fontSize = 25.sp,
                style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            IconButton(
                onClick = {
                    Toast.makeText(
                        context, "Botão para adicionar contas", Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.background(Branco)
            ) {
                Icon(Icons.Outlined.AddCircle,
                    "Adicionar contas secundárias",
                    modifier = Modifier.size(28.dp)
                        .background(Branco))
            }
        })
}

@Composable
@Preview
private fun Preview(){
    BreezeTheme {
        BreezeTopAppBar()
    }

}
