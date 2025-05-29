package com.migueldk17.breeze.repository

import com.migueldk17.breeze.dao.ParcelaDao
import com.migueldk17.breeze.entity.ParcelaEntity
import javax.inject.Inject

class ParcelaRepository @Inject constructor(
    private val parcelaDao: ParcelaDao
){
    suspend fun adicionaParcelas(parcelaEntity: ParcelaEntity){

    }
}