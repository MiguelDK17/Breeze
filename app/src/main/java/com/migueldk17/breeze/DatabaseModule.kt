package com.migueldk17.breeze

import android.content.Context
import androidx.room.Room
import com.migueldk17.breeze.data.local.dao.ContaDao
import com.migueldk17.breeze.data.local.dao.ParcelaDao
import com.migueldk17.breeze.data.local.dao.MovimentacaoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BreezeDatabase {

        return Room.databaseBuilder(
            context,
            BreezeDatabase::class.java,
            "breeze_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()


    }
    @Provides
    fun provideSaldoDao(database: BreezeDatabase): MovimentacaoDao {
        return database.receitaDao()
    }
    @Provides
    fun provideContaDao(database: BreezeDatabase): ContaDao {
        return database.contaDao()
    }
    @Provides
    fun provideParcelaDao(database: BreezeDatabase): ParcelaDao {
        return database.parcelaDao()
    }
}