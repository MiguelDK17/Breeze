package com.migueldk17.breeze.dto

import java.math.BigDecimal

data class CategoryTotalDto(
    val category: String, //Categoria das contas
    val totalAmount: BigDecimal, //Valor total da categoria
)