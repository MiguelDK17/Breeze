package com.migueldk17.breeze

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.migueldk17.breeze.dao.ContaDao
import com.migueldk17.breeze.dao.ParcelaDao
import com.migueldk17.breeze.dao.ReceitaDao
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
    fun provideSaldoDao(database: BreezeDatabase): ReceitaDao {
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