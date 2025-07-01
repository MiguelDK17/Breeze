package com.migueldk17.breeze

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.migueldk17.breeze.ui.features.historico.ui.layouts.HistoricoDoMes
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.theme.BreezeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity4: ComponentActivity() {
    private val viewModel by viewModels<HistoricoDoMesViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val mes = intent.getStringExtra("mes")
            val dataFormatada = intent.getStringExtra("dataFormatada")
            if (dataFormatada != null) {
                Log.d(TAG, "dataFormatada: caiu no if: $dataFormatada")
                viewModel.setData(dataFormatada)
            }
            else {
                Log.d(TAG, "dataFormatada: caiu no else: $dataFormatada")
            }
            BreezeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Mês de $mes")
                            }
                        )
                    }
                ) { paddingValues ->
                    HistoricoDoMes(modifier = Modifier.padding(paddingValues),viewModel)
                }
            }
        }
    }
}