package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.theme.NavyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsCard(
    onChangeOpenDialog: () -> Unit
    ){

        BasicAlertDialog(
            onDismissRequest = {
                //Dispensa o BasicAlertDialog
                onChangeOpenDialog()
            }
        ) {
            Surface(
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(30.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    //Título do BasicAlertDialog
                    TitleText("Detalhes da Conta",
                        color = NavyBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                        DescriptionText(
                            "Nome:",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        DescriptionText(
                            "Valor Total:",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        DescriptionText(
                            "Valor da parcela:",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        DescriptionText(
                            "Data de pagamento:",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        DescriptionText(
                            "Taxa de juros:",
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            onChangeOpenDialog() //Botão de confirmar
                        },
                            shape = ShapeDefaults.ExtraSmall) {
                            Text("Tudo bem!")
                        }
                    }
                }
            }
        }
}