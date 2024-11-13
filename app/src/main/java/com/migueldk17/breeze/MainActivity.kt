package com.migueldk17.breeze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.migueldk17.breeze.ui.components.BreezeBottomBar
import com.migueldk17.breeze.ui.components.BreezeTopAppBar
import com.migueldk17.breeze.ui.layouts.PaginaInicial
import com.migueldk17.breeze.ui.theme.BreezeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreezeTheme {
                val scroll = rememberScrollState()
                Scaffold(modifier = Modifier
                    .fillMaxSize(),
                    topBar = {
                        BreezeTopAppBar()
                    },
                    bottomBar = {
                        BreezeBottomBar()
                    },){ innerPadding ->
                    PaginaInicial(modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(scroll))
                }
            }
        }
    }

}

