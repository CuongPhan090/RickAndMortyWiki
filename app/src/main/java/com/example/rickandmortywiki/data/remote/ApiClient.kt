package com.example.rickandmortywiki.data.remote

import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdPagingSource
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdResponse
import com.example.rickandmortywiki.model.networkresponse.GetListOfCharacter
import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun getCharacterById(character: Int): SimpleResponse<CharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(character) }
    }

    suspend fun getListOfCharacters(pageIndex: Int): SimpleResponse<GetListOfCharacter> {
        return safeApiCall { rickAndMortyService.getListOfCharacter(pageIndex) }
    }

    suspend fun getSingleEpisode(episodeId: Int): SimpleResponse<EpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getSingleEpisode(episodeId) }
    }

    suspend fun getListOfEpisode(episodeRange: String): SimpleResponse<List<EpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getListOfEpisode(episodeRange) }
    }

    suspend fun getEpisodeByPageId(pageIndex: Int): SimpleResponse<EpisodeByIdPagingSource> {
        return safeApiCall { rickAndMortyService.getEpisodeByPageId(pageIndex) }
    }


    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
