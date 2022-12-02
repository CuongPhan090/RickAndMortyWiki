package com.example.rickandmortywiki.repository

import com.example.rickandmortywiki.data.remote.NetworkCache
import com.example.rickandmortywiki.data.remote.NetworkLayer
import com.example.rickandmortywiki.model.DataTransformUtils
import com.example.rickandmortywiki.model.domain.Characters
import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdPagingSource
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdResponse
import com.example.rickandmortywiki.model.networkresponse.GetListOfCharacter

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): Characters? {
        val cachedCharacter = NetworkCache.characterResponseMap
        cachedCharacter[characterId]?.let {
            return it
        } ?: run {
            val request = NetworkLayer.apiClient.getCharacterById(characterId)
            // User device network issue
            if (request.failed) {
                return null
            }

            // Api issue
            if (!request.isSuccessful)
                return null

            val characterResponseBody = request.body
            val episodeResponseBody = parseEpisodeFromCharacterResponse(characterResponseBody)
            val character = DataTransformUtils.transformCharacterResponseToCharacter(
                response = characterResponseBody,
                episode = episodeResponseBody
            )
            cachedCharacter[characterId] = character
            return character
        }
    }

    suspend fun getListOfCharacters(pageIndex: Int): GetListOfCharacter? {
        val request = NetworkLayer.apiClient.getListOfCharacters(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }

    suspend fun getEpisodeByPageId(pageIndex: Int): EpisodeByIdPagingSource? {
        val request = NetworkLayer.apiClient.getEpisodeByPageId(pageIndex)

        if (!request.isSuccessful) {
            return null
        }

        return request.body
    }

    private suspend fun parseEpisodeFromCharacterResponse(characterResponse: CharacterByIdResponse?): List<EpisodeByIdResponse>? {
        val episodeRange = characterResponse?.episode?.map { episode ->
            episode.substring(episode.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getListOfEpisode(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}
