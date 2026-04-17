package com.migueldk17.breeze.mapper.dto

import com.migueldk17.breeze.domain.CategoryExpense
import com.migueldk17.breeze.dto.CategoryTotalDto
import java.math.BigDecimal

fun List<CategoryTotalDto>.toDomain(): List<CategoryExpense> {
    val totalGeral = sumOf { it.totalAmount }

    if (totalGeral == BigDecimal.ZERO) return emptyList()

    return map {
        CategoryExpense(
            category = it.category,
            totalAmount = it.totalAmount,
            percentage = it.totalAmount.divide(totalGeral).toFloat()
        )
    }.sortedByDescending { it.totalAmount }

}