package com.migueldk17.breeze.ui.features.paginainicial.ui.components

import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
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
    ){
    val nome = mapDeCategoria["Nome"]!!
    val mapDeCategoriaMutavel = mapDeCategoria.toMutableMap()

    val lista = listOf(
        "Nome",
        "Categoria",
        "Sub Categoria",
        "Valor Total",
        "Valor da parcela",
        "Data de pagamento",
        "Taxa de juros"
    )
    val indicesParaRemover = setOf(4, 6)
    val listaFiltrada = if (nome.contains("Parcela")) lista else lista
        .filterIndexed { index, _ -> index !in indicesParaRemover }

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
                    Spacer(modifier = Modifier
                        .height(20.dp)
                        .background(Color.Yellow))
                    listaFiltrada.forEach { category ->
                        val accountCategory = mapDeCategoriaMutavel[category]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            DescriptionText(
                                category,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                            Log.d(TAG, "DetailsCard: $accountCategory")

                            DescriptionText(
                                " $accountCategory",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .align(Alignment.Top)
                                    .background(Color.Yellow),
                                size = if (accountCategory!!.contains("Assinaturas")) 11.9.sp else 14.sp
                                )



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
                            shapes = ButtonShapes(
                                shape = ShapeDefaults.ExtraSmall,
                                pressedShape = ShapeDefaults.ExtraSmall),
                            modifier = Modifier
                                    .height(48.dp)) {
                            Text("Tudo bem!")
                        }
                    }
                }
            }
        }
}


