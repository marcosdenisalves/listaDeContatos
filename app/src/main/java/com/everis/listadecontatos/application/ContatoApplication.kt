package com.everis.listadecontatos.application

import android.app.Application
import com.everis.listadecontatos.helpers.HelperDB

class ContatoApplication : Application() {
<<<<<<< HEAD
=======

>>>>>>> 7dcb7d516eceea2031d7ff0bef80d085661ff03e
    var helperDB: HelperDB? = null
        private set

    companion object {
        lateinit var instance: ContatoApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}