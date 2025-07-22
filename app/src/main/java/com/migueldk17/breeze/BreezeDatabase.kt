package com.migueldk17.breeze

import androidx.room.Database
import androidx.room.RoomDatabase
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.ParcelaDao
import com.migueldk17.breeze.dao.ReceitaDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.entity.Receita


@Database(entities = [Conta::class, Receita::class, ParcelaEntity::class], version = 9, exportSchema = true)
abstract class BreezeDatabase: RoomDatabase() {
    abstract fun receitaDao(): ReceitaDao

    abstract fun contaDao(): ContaDao

    abstract fun parcelaDao(): ParcelaDao
  
}