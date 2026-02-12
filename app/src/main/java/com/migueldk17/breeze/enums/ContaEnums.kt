package com.migueldk17.breeze.enums

enum class StatusConta {
    PENDENTE, //Conta pendente de pagamento, porém em dia
    PAGA, //Conta já paga pelo usuário
    ATRASADA, // Conta atrasada
    INVALIDO //Valor invalido interno
}