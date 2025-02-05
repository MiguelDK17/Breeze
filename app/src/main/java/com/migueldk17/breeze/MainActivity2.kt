package com.migueldk17.breeze


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.migueldk17.breeze.ui.layouts.EditarValorConta
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2: ComponentActivity() {
    private val viewModel by viewModels<BreezeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreezeTheme {
            Scaffold { paddingValues ->
                //Pega o array de colors enviado de PaginaInicial
                val recuperaCor = intent.getIntArrayExtra("color")
                //Pega o nome da conta enviado de PaginaInicial
                val nome = intent.getStringExtra("nome")
                //Envia o array de cores para o ViewModel
                viewModel.transformaCor(recuperaCor!!)
                //Envia o nome para o ViewModel
                viewModel.setNomeConta(nome!!)
                EditarValorConta(viewModel, modifier = Modifier.padding(paddingValues))
            }
            }
        }
    }
}