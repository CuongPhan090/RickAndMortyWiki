package com.example.rickandmortywiki.repository

import com.example.rickandmortywiki.model.networkresponse.GetListOfCharacter
import com.example.rickandmortywiki.data.remote.NetworkLayer
import com.example.rickandmortywiki.model.domain.Characters
import com.example.rickandmortywiki.model.DataTransformUtils
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdResponse

class SharedRepository {
    suspend fun getCharacterById(character: Int): Characters? {
        val request = NetworkLayer.apiClient.getCharacterById(character)

        // User device network issue
        if (request.failed) {
            return null
        }

        // Api issue
        if (!request.isSuccessful)
            return null

        val characterResponseBody = request.body
        val episodeResponseBody = parseEpisodeFromCharacterResponse(characterResponseBody)
        return DataTransformUtils.transformCharacterResponseToCharacter(
            response = characterResponseBody,
            episode = episodeResponseBody
        )
    }

    suspend fun getListOfCharacters(pageIndex: Int): GetListOfCharacter? {
        val request = NetworkLayer.apiClient.getListOfCharacters(pageIndex)

        if (request.failed || !request.isSuccessful) {
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
