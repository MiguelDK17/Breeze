package com.migueldk17.breeze

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.ui.features.historico.ui.layouts.HistoricoDoMes
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel

class MainActivity4: ComponentActivity() {
    private val viewModel by viewModels<HistoricoViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setContent{
            val data = viewModel.dataTraduzida.collectAsStateWithLifecycle().value
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Mês de $data")
                        }
                    )
                }
            ) { _ ->
                HistoricoDoMes(viewModel)
            }
        }
    }
}