package com.geek.notekeeper

import android.app.Application
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

const val appId = "notekeeper-bhavl"
lateinit var noteApp: App

class NoteApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        noteApp = App(AppConfiguration.Builder(appId).build())

    }
}