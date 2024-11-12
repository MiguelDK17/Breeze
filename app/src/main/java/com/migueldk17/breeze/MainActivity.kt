package com.migueldk17.breeze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BreezeBottomBar()
                    }){ innerPadding ->
                    BreezeTopAppBar()
                    PaginaInicial(modifier = Modifier.padding(innerPadding))


                }
            }
        }
    }
}

