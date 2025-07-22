package com.migueldk17.breeze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.historico.ui.layouts.HistoricoDoMesConta
import com.migueldk17.breeze.ui.features.historico.ui.layouts.HistoricoDoMesReceita
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoReceitaViewModel
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.utils.ToastManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity4: ComponentActivity() {
    private val viewModelContas by viewModels<HistoricoDoMesViewModel>()
    private val viewModelReceitas by viewModels<HistoricoReceitaViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val mes = intent.getStringExtra("mes")
            val dataFormatada = intent.getStringExtra("dataFormatada")
            if (dataFormatada != null) {
                viewModelContas.setData(dataFormatada)
                viewModelReceitas.setData(dataFormatada)
            }
            val context = LocalContext.current
            val categories = listOf("Contas", "Receitas")
            var selectedCategory by remember { mutableStateOf(categories[0]) }
            BreezeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Mês de $mes")
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            ),
                            actions = {
                                DescriptionText("Filtros:")
                                val isButtonMoneySendSelected = selectedCategory == categories[0]

                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .combinedClickable(
                                            onClick = {
                                                selectedCategory = categories[0]
                                            },
                                            onLongClick = {
                                                ToastManager.showToast(context, "Contas")
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    BreezeIcon(
                                        BreezeIcons.Linear.Money.MoneySend,
                                        contentDescription = null,
                                        color = buttonColor(isButtonMoneySendSelected)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .combinedClickable(
                                            onClick = {
                                                selectedCategory = categories[1]
                                            },
                                            onLongClick = {
                                                ToastManager.showToast(context, "Receitas")
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val isButtonMoneyReciveSelected = selectedCategory == categories[1]
                                    BreezeIcon(
                                        BreezeIcons.Linear.Money.MoneyRecive,
                                        contentDescription = null,
                                        color = buttonColor(isButtonMoneyReciveSelected)
                                    )

                                }

                            }
                        )
                    }
                ) { paddingValues ->
                    if (selectedCategory == "Contas") {
                        HistoricoDoMesConta(
                            modifier = Modifier.padding(paddingValues),
                            viewModelContas,
                        )
                    }
                    else {
                        HistoricoDoMesReceita(
                            modifier = Modifier.padding(paddingValues),
                            viewModelReceita = viewModelReceitas
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun buttonColor(isButtonSelected: Boolean): Color{
    val color = if (isButtonSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    return color
}