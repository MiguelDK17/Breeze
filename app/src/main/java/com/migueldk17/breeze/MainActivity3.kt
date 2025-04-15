package com.migueldk17.breeze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.AdicionarContaOpcional
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity3: ComponentActivity() {
    private val viewModel by viewModels<BreezeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BreezeTheme {

                Scaffold {paddingValues ->
                    AdicionarContaOpcional(modifier = Modifier.padding(paddingValues), viewModel)
                }
            }
        }
    }
}