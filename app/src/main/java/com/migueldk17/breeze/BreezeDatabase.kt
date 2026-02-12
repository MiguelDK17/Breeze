package com.migueldk17.breeze

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.migueldk17.breeze.converters.TipoMovimentacaoConverter
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.ParcelaDao
import com.migueldk17.breeze.dao.MovimentacaoDao
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.entity.MovimentacaoEntity


@Database(entities = [Conta::class, MovimentacaoEntity::class, ParcelaEntity::class], version = 17, exportSchema = true)
@TypeConverters(TipoMovimentacaoConverter::class)
abstract class BreezeDatabase: RoomDatabase() {

    abstract fun receitaDao(): MovimentacaoDao

    abstract fun contaDao(): ContaDao

    abstract fun parcelaDao(): ParcelaDao
  
}