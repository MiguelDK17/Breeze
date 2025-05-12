package com.migueldk17.breeze.ui.features.historico.model

import com.migueldk17.breeze.entity.Conta
import java.time.LocalDate

//Model de Historico com data, conta principal(para ser mostrado em primeiro na linha do tempo)
//E outras contas que ficam escondidas sob o estado do botão ver mais
data class HistoricoDoDia(
    val data: LocalDate, //Data de criação da conta
    val contaPrincipal: Conta, //Conta principal
    val outrasContas: List<Conta> //Outras contas que ficam escondidas sob o estado do botão ver mais em HistoricoItem
)
