package com.migueldk17.breeze.provider

import java.time.LocalDate

interface DateProvider {
    fun today(): LocalDate
}

class DefaultDateProvider: DateProvider {
    override fun today(): LocalDate = LocalDate.now()
}