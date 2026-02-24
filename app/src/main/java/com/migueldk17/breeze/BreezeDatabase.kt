package com.migueldk17.breeze

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.migueldk17.breeze.converters.TipoMovimentacaoConverter
import com.migueldk17.breeze.data.local.dao.ContaDao
import com.migueldk17.breeze.data.local.dao.ParcelaDao
import com.migueldk17.breeze.data.local.dao.MovimentacaoDao
import com.migueldk17.breeze.data.local.entity.Conta
import com.migueldk17.breeze.data.local.entity.ParcelaEntity
import com.migueldk17.breeze.data.local.entity.MovimentacaoEntity


@Database(entities = [Conta::class, MovimentacaoEntity::class, ParcelaEntity::class], version = 19, exportSchema = true)
@TypeConverters(TipoMovimentacaoConverter::class)
abstract class BreezeDatabase: RoomDatabase() {

    abstract fun receitaDao(): MovimentacaoDao

    abstract fun contaDao(): ContaDao

    abstract fun parcelaDao(): ParcelaDao
  
}