package com.migueldk17.breeze.database

import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.migueldk17.breeze.BreezeDatabase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReceitaMigrationTest {
    private val TEST_DB = "breeze-test.db"

    private val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        BreezeDatabase::class.java
    )

    @Test
    fun migrateTo9() {
        // Cria o banco na versão antiga (V8)
        val db = helper.createDatabase(TEST_DB, 8)

        // Cria a tabela antiga (caso teu schema não crie automaticamente)
        db.execSQL("""
            CREATE TABLE saldo_table(
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            valor REAL NOT NULL,
            descricao TEXT NOT NULL,
            data TEXT NOT NULL
            )
        """.trimIndent())
            db.close()

        //Executa a migração
        val migrateDb = helper.runMigrationsAndValidate(
            TEST_DB,
            9,
            true,
            MIGRATION_8_9
        )
        //Valida se os dados migraram corretamente
        val cursor = migrateDb.query("SELECT * from receita_entity")

        assert(cursor.moveToFirst())

        val descricaoIndex = cursor.getColumnIndex("descricao")
        val descricao = cursor.getString(descricaoIndex)

        val valorIndex = cursor.getColumnIndex("valor")
        val valor = cursor.getDouble(valorIndex)

        val dataIndex = cursor.getColumnIndex("data")
        val data = cursor.getString(dataIndex)

        val iconIndex = cursor.getColumnIndex("icon")
        val icon = cursor.getString(iconIndex)

    }
}