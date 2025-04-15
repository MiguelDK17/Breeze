package com.migueldk17.breeze.ui.features.configuracoes.ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
fun Configuracoes(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("-Faça login para salvar suas contas na nuvem-")
        Spacer(modifier = Modifier.size(20.dp))
        TextButton(onClick = {}) {
            Text("Fazer Login Com Google",
                style = TextStyle(color = MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.size(15.dp))
            BreezeIcon(
                BreezeIcons.Linear.Company.GoogleLinear,
                contentDescription = "Google",
                color = MaterialTheme.colorScheme.secondary)
        }
    }
}
@Composable
@Preview(showBackground = true)
private fun Preview(){
    BreezeTheme {
        Configuracoes()
    }
}