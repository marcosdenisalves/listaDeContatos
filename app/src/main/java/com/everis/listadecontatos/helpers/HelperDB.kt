package com.everis.listadecontatos.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.everis.listadecontatos.feature.listacontatos.model.ContatosVO

class HelperDB(
<<<<<<< HEAD
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
=======
    context: Context
) : SQLiteOpenHelper(context, NOME_BANCO, null, VERSAO_ATUAL) {

    companion object {
        private val NOME_BANCO = "contato.db"
        private val VERSAO_ATUAL = 2
    }

    val TABLE_NAME = "contato"
    val COLUMNS_ID = "id"
    val COLUMNS_NOME = "nome"
    val COLUMNS_TELEFONE = "telefone"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL," +
            "$COLUMNS_NOME TEXT NOT NULL," +
            "$COLUMNS_TELEFONE TEXT NOT NULL," +
            "" +
            "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)" +
            ")"
>>>>>>> 7dcb7d516eceea2031d7ff0bef80d085661ff03e

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
<<<<<<< HEAD
        if (oldVersion != newVersion) {
=======
        if(oldVersion != newVersion) {
>>>>>>> 7dcb7d516eceea2031d7ff0bef80d085661ff03e
            db?.execSQL(DROP_TABLE)
        }
        onCreate(db)
    }

<<<<<<< HEAD
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
=======
    fun buscarContatos(busca: String, isBuscaPorID: Boolean = false) : List<ContatosVO> {
        val db = readableDatabase ?: return mutableListOf()
        var lista = mutableListOf<ContatosVO>()
        var where: String? = null
        var args: Array<String> = arrayOf()
        if(isBuscaPorID){
            where = "$COLUMNS_ID = ?"
            args = arrayOf("$busca")
        }else{
            where = "$COLUMNS_NOME LIKE ?"
            args = arrayOf("%$busca%")
        }
        var cursor = db.query(TABLE_NAME,null,where,args,null,null,null)
        if (cursor == null){
            db.close()
            return mutableListOf()
        }
        while(cursor.moveToNext()){
            var contato = ContatosVO(
                cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUMNS_TELEFONE))
>>>>>>> 7dcb7d516eceea2031d7ff0bef80d085661ff03e
            )
            lista.add(contato)
        }
        db.close()
        return lista
    }

    fun salvarContato(contato: ContatosVO) {
<<<<<<< HEAD
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
=======
        val db = writableDatabase ?: return
        var content = ContentValues()
        content.put(COLUMNS_NOME,contato.nome)
        content.put(COLUMNS_TELEFONE,contato.telefone)
        db.insert(TABLE_NAME,null,content)
        db.close()
    }

    fun deletarCoontato(id: Int) {
        val db = writableDatabase ?: return
        val sql = "DELETE FROM $TABLE_NAME WHERE $COLUMNS_ID = ?"
        val arg = arrayOf("$id")
        db.execSQL(sql,arg)
        db.close()
    }

    fun updateContato(contato: ContatosVO) {
        val db = writableDatabase ?: return
        val sql = "UPDATE $TABLE_NAME SET $COLUMNS_NOME = ?, $COLUMNS_TELEFONE = ? WHERE $COLUMNS_ID = ?"
        val arg = arrayOf(contato.nome,contato.telefone,contato.id)
        db.execSQL(sql,arg)
>>>>>>> 7dcb7d516eceea2031d7ff0bef80d085661ff03e
        db.close()
    }
}