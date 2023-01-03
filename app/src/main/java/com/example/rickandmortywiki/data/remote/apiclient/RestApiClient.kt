package com.example.rickandmortywiki.data.remote.apiclient

import com.example.rickandmortywiki.data.remote.SimpleResponse
import com.example.rickandmortywiki.model.networkresponse.GetListOfCharacter
import retrofit2.Response

class RestApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun searchCharacters(characterName: String?, pageIndex: Int?): SimpleResponse<GetListOfCharacter> {
        return safeApiCall { rickAndMortyService.searchCharacters(characterName, pageIndex) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
