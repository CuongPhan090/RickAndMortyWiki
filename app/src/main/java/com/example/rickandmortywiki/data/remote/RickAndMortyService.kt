package com.example.rickandmortywiki.data.remote

import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdPagingSource
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdResponse
import com.example.rickandmortywiki.model.networkresponse.GetListOfCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path("character-id") characterId: Int): Response<CharacterByIdResponse>

    @GET("character/")
    suspend fun getListOfCharacter(@Query("page") pageIndex: Int): Response<GetListOfCharacter>

    @GET("character/")
    suspend fun searchCharacters(@Query("name") name: String?, @Query("page") page: Int?): Response<GetListOfCharacter>

    @GET("episode/{episode-id}")
    suspend fun getSingleEpisode(@Path("episode-id") episodeId: Int): Response<EpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getListOfEpisode(@Path("episode-range") episodeRange: String): Response<List<EpisodeByIdResponse>>

    @GET("character/{character-range}")
    suspend fun getMultipleCharacters(@Path("character-range") characterRange: String): Response<List<CharacterByIdResponse>>

    @GET("episode/")
    suspend fun getEpisodeByPageId(@Query("page") pageIndex: Int): Response<EpisodeByIdPagingSource>

} 