package com.migueldk17.breeze

import androidx.room.Database
import androidx.room.RoomDatabase
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.SaldoDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.Saldo

@Database(entities = [Saldo::class, Conta::class], version = 1, exportSchema = false)
abstract class BreezeDatabase: RoomDatabase() {
    abstract fun saldoDao(): SaldoDao

    abstract fun contaDao(): ContaDao
}