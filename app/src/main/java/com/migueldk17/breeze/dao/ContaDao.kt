package com.migueldk17.breeze.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.migueldk17.breeze.entity.Conta
import kotlinx.coroutines.flow.Flow

@Dao
interface ContaDao {
    @Query("SELECT * FROM conta_table")
     fun getConta(): Flow<List<Conta>>

     @Query("SELECT ")

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConta(conta: Conta)

    @Update
    suspend fun atualizarConta(conta: Conta)
}