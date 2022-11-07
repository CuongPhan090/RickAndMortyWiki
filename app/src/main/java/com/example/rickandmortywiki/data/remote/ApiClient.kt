package com.example.rickandmortywiki.data.remote

import com.example.rickandmortywiki.data.model.GetCharacterByIdResponse
import com.example.rickandmortywiki.data.model.GetListOfCharacter
import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun getCharacterById(character: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(character) }
    }

    suspend fun getListOfCharacters(pageIndex: Int): SimpleResponse<GetListOfCharacter> {
        return safeApiCall { rickAndMortyService.getListOfCharacter(pageIndex) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
