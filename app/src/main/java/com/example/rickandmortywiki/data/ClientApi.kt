package com.example.rickandmortywiki.data

import com.example.rickandmortywiki.model.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface ClientApi {

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character

}