package com.migueldk17.breeze.ui.layouts.adicionarconta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.icons.BreezeIcon
import com.migueldk17.breeze.ui.icons.BreezeIcons
import com.migueldk17.breeze.ui.theme.SkyBlue
import com.migueldk17.breeze.ui.theme.blackPoppins

@Composable
fun Final() {
    Column(
        modifier = Modifier
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Sua nova conta está pronta! Quando precisar, ela estará aqui para te ajudar.",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(25.dp))
        Card(
            modifier = Modifier
                .size(width = 342.dp, height = 80.dp)
            ,
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardColors(
                containerColor = SkyBlue,
                contentColor = Color.Black,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )


        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
            ) {
                BreezeIcon(
                    BreezeIcons.Linear.BookLinear,
                    contentDescription = "Ícone de Livro",
                    modifier = Modifier
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.size(15.dp))
                Column {
                    Text("Nome da conta",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = blackPoppins,
                        ),
                        modifier = Modifier.padding(5.dp))
                    Text("Valor da Conta",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = blackPoppins
                        ),
                        modifier = Modifier.padding(5.dp))
                }
            }
        }
        Spacer(modifier = Modifier.size(35.dp))




            OutlinedButton(
                onClick = {

                }
            ) {
                Text("Adicionar nova conta")
            }
        Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = {

                }
            ) {
                Text("Concluir")
            }



    }
}