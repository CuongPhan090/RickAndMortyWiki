package com.example.rickandmortywiki.repository

import com.example.rickandmortywiki.data.model.GetCharacterByIdResponse
import com.example.rickandmortywiki.data.model.GetListOfCharacter
import com.example.rickandmortywiki.data.remote.NetworkLayer

class SharedRepository {
    suspend fun getCharacterById(character: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(character)

        // User device network issue
        if (request.failed) {
            return null
        }

        // Api issue
        if (!request.isSuccessful)
            return null

        return request.body
    }

    suspend fun getListOfCharacters(pageIndex: Int): GetListOfCharacter? {
        val request = NetworkLayer.apiClient.getListOfCharacters(pageIndex)

        if (request.failed) {
            return null
        }

        if (!request.isSuccessful) {
            return null
        }

        return request.body
    }
}