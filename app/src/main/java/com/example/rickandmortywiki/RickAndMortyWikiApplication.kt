package com.example.rickandmortywiki

import android.app.Application
import android.content.Context

class RickAndMortyWikiApplication: Application() {

    companion object {
        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()
        context = this
    }
}