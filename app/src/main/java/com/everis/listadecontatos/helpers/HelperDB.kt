package com.everis.listadecontatos.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class HelperDB(
    context: Context?
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {
    companion object {
        private val NOME_BANCO = "contato.db"
        private val VERSAO_ATUAL = 1
    }

    val TABLE_NAME = "contato"
    val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
          id INTEGER NOT NULL,
          nome TEXT NOT NULL,
          telefone TEXT NOT NULL,
          PRIMARY KEY (id AUTOINCREMENT)
        )
    """.trimIndent()
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

    fun buscarContatos(isBuscaPorID: Boolean = false, arg: String): List<ContatosVO> {
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<ContatosVO>()
        var args: Array<String> = arrayOf()
        var where: String? = null

        if (isBuscaPorID) {
            where = "id = ?"
            args = arrayOf(arg)
        } else {
            where = "nome LIKE ?"
            args = arrayOf("%$arg%")
        }
        val queryResult = db.query(TABLE_NAME, null, where, args, null, null, null)

        if (queryResult == null) {
            db.close()
            return mutableListOf()
        }
        while (queryResult.moveToNext()) {
            var contato = ContatosVO(
                queryResult.getInt(queryResult.getColumnIndex("id")),
                queryResult.getString(queryResult.getColumnIndex("nome")),
                queryResult.getString(queryResult.getColumnIndex("telefone"))
            )
            lista.add(contato)
        }
        db.close()
        return lista
    }

    fun salvarContato(contato: ContatosVO) {
        var list = buscarContatos(false, "")
        list.forEach {
            if (contato.nome == it.nome)
                return
        }
        val db = writableDatabase ?: return
        var content = ContentValues()
        content.put("nome", contato.nome)
        content.put("telefone", contato.telefone)
        db.insert("contato", null, content)
        db.close()
    }

    fun deletaContato(id: Int) {
        val db = writableDatabase ?: return
        val where = "id = ?"
        val args = arrayOf("$id")
        db.delete(TABLE_NAME, where, args)
        db.close()
    }
}