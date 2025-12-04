package com.migueldk17.breeze

import android.app.Activity
import android.os.Bundle
import android.view.WindowInsetsController
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeFABMenu
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.features.historico.ui.layouts.HistoricoDoMesConta
import com.migueldk17.breeze.ui.features.historico.ui.layouts.HistoricoDoMesReceita
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoReceitaViewModel
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.utils.ToastManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity4: ComponentActivity() {
    private val viewModelContas by viewModels<HistoricoDoMesViewModel>()
    private val viewModelReceitas by viewModels<HistoricoReceitaViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val view = LocalView.current

            LaunchedEffect(Unit) {
                val window = (view.context as Activity).window
                WindowCompat.setDecorFitsSystemWindows(window, false)

                val controller = WindowInsetsControllerCompat(window, view)
                controller.hide(WindowInsetsCompat.Type.statusBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            val mes = intent.getStringExtra("mes")
            val dataFormatada = intent.getStringExtra("dataFormatada")
            if (dataFormatada != null) {
                viewModelContas.setData(dataFormatada)
                viewModelReceitas.setData(dataFormatada)
            }
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
                            )
                        )
                    },
                    floatingActionButton = {
                        BreezeFABMenu(
                            onChangeSelectedCategory = {
                                selectedCategory = it
                            }
                        )
                    },
                    floatingActionButtonPosition = FabPosition.End,

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

