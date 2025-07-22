package com.migueldk17.breeze.database

import android.util.Log
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.migueldk17.breeze.BreezeDatabase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

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
        db.query("SELECT * FROM saldo_table")
            .use{
                while (it.moveToFirst()){
                   println("Tabela encontrada: ${it.getString(0)}")
                }
            }


        db.execSQL("""
            INSERT INTO saldo_table (valor, descricao, data)
            VALUES (99.99, 'Teste Receita', '2023-10-01')
        """.trimIndent())

        db.close()

        val MIGRATION_8_9 = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Cria a nova tabela com a estrutura nova
                database.execSQL("""
                    CREATE TABLE receita_entity (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        valor REAL NOT NULL,
                        descricao TEXT NOT NULL,
                        data TEXT NOT NULL,
                        icon TEXT NOT NULL DEFAULT ''
                    )
                """.trimIndent())
                // Copia os dados da tabela saldo_table para a nova tabela
                database.execSQL("""
                    INSERT INTO receita_entity (id, valor, descricao, data, icon)
                    SELECT id, valor, descricao, data, '' FROM saldo_table
                """.trimIndent())

                // Remove a tabela antiga
                database.execSQL("DROP TABLE saldo_table")
            }
        }


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

        assertEquals("Teste Receita", descricao)
        assertEquals(99.99, valor, 0.01)
        assertEquals("2023-10-01", data)
        assertEquals("", icon)

    }
}