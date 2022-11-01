package com.example.rickandmortywiki.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Remote {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val apiClient = retrofit.create(ClientApi::class.java)

    suspend fun getCharacterById(character: Int) = apiClient.getCharacterById(character)

}