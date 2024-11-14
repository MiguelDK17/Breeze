package com.migueldk17.breeze.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.theme.BreezeTheme


@Composable
fun Calendario(){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "2024",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Grid com os meses
            GridMes()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {
                    Toast.makeText(
                        context,"Vc cancelou a ação kkkkkkk", Toast.LENGTH_SHORT
                    ).show()
                }
                ) {
                    Text("Cancelar")
                }
                Button(onClick = {
                    Toast.makeText(
                        context, "Vc confirmou a ação, boa", Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text("Confirmar")
                }
            }

        }
    }
}

@Composable
fun GridMes(){
    val meses = listOf("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez")

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 3 colunas para os meses
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // padding para evitar que os meses fiquem colados nas bordas
    ) {
        items(meses.size) {index ->
            MesItem(meses[index])
        }
    }

}

@Composable
fun MesItem(mes: String){
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(64.dp)
            .clip(RoundedCornerShape(12.dp)) //Tamanho de cada item
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)) //Fundo com cor clara
    ){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           TextButton(onClick = {},
               modifier = Modifier.fillMaxSize()) {
               Text(
                   text = mes,
                   style = MaterialTheme.typography.bodyMedium, //Estilo de Texto
                   color = MaterialTheme.colorScheme.onSurface
               )
           }
        }
    }
}
@Composable
@Preview(showBackground = true)
private fun Preview(){
    BreezeTheme {
        Calendario()
    }
}