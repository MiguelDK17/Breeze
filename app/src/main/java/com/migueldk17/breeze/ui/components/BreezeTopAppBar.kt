package com.migueldk17.breeze.ui.components


import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.MainActivity3
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
                    val intent = Intent(context, MainActivity3::class.java)
                    context.startActivity(intent)
                },
            ) {
                Icon(Icons.Default.AddCircle,
                    "Adicionar contas secundárias",
                    modifier = Modifier.size(28.dp))
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
