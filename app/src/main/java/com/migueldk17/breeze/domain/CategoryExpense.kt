package com.migueldk17.breeze.domain

import java.math.BigDecimal

data class CategoryExpense(
    val category: String,
    val totalAmount: BigDecimal,
    val percentage: Float,
)
