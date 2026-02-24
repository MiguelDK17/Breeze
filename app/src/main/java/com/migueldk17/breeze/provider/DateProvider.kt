package com.migueldk17.breeze.provider

import jakarta.inject.Inject
import java.time.LocalDate


interface DateProvider {
    fun today(): LocalDate
}


class DefaultDateProvider @Inject constructor(): DateProvider {
    override fun today(): LocalDate = LocalDate.now()
}