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
import com.migueldk17.breeze.viewmodels.BreezeViewModel

class MainActivity2: ComponentActivity() {
    private val viewModel by viewModels<BreezeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { paddingValues ->
            val recuperaCor = intent.getIntArrayExtra("color")
                val nome = intent.getStringExtra("nome")
                viewModel.transformaCor(recuperaCor!!)
                viewModel.setNome(nome!!)
                EditarValorConta(viewModel, modifier = Modifier.padding(paddingValues))

            }
        }
    }
}