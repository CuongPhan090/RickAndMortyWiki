package com.example.rickandmortywiki.repository

import com.example.rickandmortywiki.data.model.GetCharacterByIdResponse
import com.example.rickandmortywiki.data.remote.NetworkLayer

class SharedRepository {
    suspend fun getCharacterById(character: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(character)
        if (request.isSuccessful) {
            return request.body()
        }
        else {
            return null
        }
    }
}