package com.migueldk17.breeze


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.EditarValorConta
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
                //Pega a conta uma vez
                LaunchedEffect(id) { viewModel.pegaContaSelecionada(id)}

                val conta by viewModel.contaSelecionada.collectAsState()
                if(conta == null){
                    Carregando()
                }
                else {
                    EditarValorConta(conta!!, id)
                }
            }
            }
        }
    }
}