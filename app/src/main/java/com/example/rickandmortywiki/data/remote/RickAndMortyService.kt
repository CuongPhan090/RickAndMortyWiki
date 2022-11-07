package com.example.rickandmortywiki.data.remote

import com.example.rickandmortywiki.data.model.GetCharacterByIdResponse
import com.example.rickandmortywiki.data.model.GetListOfCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path("character-id") characterId: Int): Response<GetCharacterByIdResponse>

    @GET("character")
    suspend fun getListOfCharacter(@Query("page") pageIndex: Int): Response<GetListOfCharacter>

} 