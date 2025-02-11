package com.migueldk17.breeze

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.SaldoDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.Saldo

@Database(entities = [Conta::class, Saldo::class], version = 1, exportSchema = false)
@TypeConverters(ColorConverter::class, BreezeIconEnumConverter::class)
abstract class BreezeDatabase: RoomDatabase() {
    abstract fun saldoDao(): SaldoDao

    abstract fun contaDao(): ContaDao
}