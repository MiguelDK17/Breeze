package com.migueldk17.breeze


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.migueldk17.breeze.ui.layouts.EditarValorConta
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.viewmodels.BreezeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2: ComponentActivity() {
    private val viewModel by viewModels<BreezeViewModel>()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreezeTheme {
            Scaffold { _ ->
                val id = intent.getIntExtra("id", 0)
                Log.d(TAG, "onCreate: $id")
                val contas by viewModel.conta.collectAsState()
                val conta = remember(contas) {
                    contas.getOrNull(id)
                }
                if(conta == null){
                    Carregando()
                }
                else {
                    EditarValorConta(viewModel, id)
                }
            }
            }
        }
    }
}