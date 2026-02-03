package com.migueldk17.breeze

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.adicionarconta.ui.layouts.AdicionarConta
import com.migueldk17.breeze.ui.features.adicionarconta.viewmodels.AdicionarContaViewModel
import com.migueldk17.breeze.ui.theme.BreezeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity3: ComponentActivity() {
    private val viewModel by viewModels<AdicionarContaViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BreezeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                //Text de título
                                TitleText(
                                    "Adicionar Conta"
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            ),
                            navigationIcon = {
                                IconButton(onClick = {
                                    finish()
                                }) {
                                    BreezeIcon(
                                        breezeIcon = BreezeIcons.Linear.Arrows.AltArrowLeft,
                                        contentDescription = "Voltar para a tela inicial"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    paddingValues ->
                    AdicionarConta(modifier = Modifier.padding(paddingValues), viewModel)
                }
            }
        }
    }
}