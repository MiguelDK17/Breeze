package com.migueldk17.breeze.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.migueldk17.breeze.entity.ParcelaEntity

interface ParcelaDao {

    @Query("SELECT * FROM parcela_entity WHERE data LIKE :mesAno || '%'")
    suspend fun getParcelasDoMes(mesAno: String): List<ParcelaEntity>

    @Query("SELECT * FROM parcela_entity WHERE id_conta_pai = :idContaPai ORDER BY numero_parcela ASC")
    suspend fun getParcelasDaConta(idContaPai: String): List<ParcelaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirParcelas(parcelas: List<ParcelaEntity>)

    @Update
    suspend fun atualizarParcela(parcela: ParcelaEntity)

    @Delete
    suspend fun deletarParcela(parcela: ParcelaEntity)


}