package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.theme.DeepSkyBlue
import com.migueldk17.breeze.ui.theme.NavyBlue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DetailsCard(
    mapDeCategoria: Map<String, String>,
    onChangeOpenDialog: (Boolean) -> Unit,
    openDialog: Boolean
    ){
    val lista = listOf("Nome:", "Valor Total:", "Valor da parcela:", "Data de pagamento:", "Taxa de juros:")
    if (openDialog){
        BasicAlertDialog(
            onDismissRequest = {
                //Dispensa o BasicAlertDialog
                onChangeOpenDialog(false)
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
                        color = if (!isSystemInDarkTheme()) NavyBlue else DeepSkyBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp).background(Color.Yellow))
                    lista.forEach { category ->
                        val accountCategory = mapDeCategoria[category]
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            DescriptionText(
                                category,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                            DescriptionText(accountCategory ?: "",
                                modifier = Modifier.padding(vertical = 5.dp))

                        }
                        Spacer(modifier = Modifier.height(10.dp))

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            onChangeOpenDialog(false) //Botão de confirmar
                        },
                            shapes = ButtonShapes(shape = ShapeDefaults.ExtraSmall, pressedShape = ShapeDefaults.ExtraSmall),
                            modifier = Modifier
                                    .height(48.dp)) {
                            Text("Tudo bem!")
                        }
                    }
                }
                }
            }
        }
}