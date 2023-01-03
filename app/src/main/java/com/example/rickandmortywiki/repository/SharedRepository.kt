package com.example.rickandmortywiki.repository

import com.example.rickandmortywiki.data.remote.NetworkLayer
import com.example.rickandmortywiki.model.DataTransformUtils
import com.example.rickandmortywiki.model.domain.Character
import com.example.rickandmortywiki.model.domain.CharacterPagination
import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.model.domain.EpisodePagination
import com.example.rickandmortywiki.model.networkresponse.GetListOfCharacter
import com.example.rickandmortywiki.type.FilterCharacter

class SharedRepository {

    suspend fun getCharacter(id: String): Character {
        val request = NetworkLayer.apolloApiClient.getCharacter(id)
        return DataTransformUtils.transformCharacterResponse1ToCharacter(
            response = request.body?.character,
            episode =  request.body?.character?.episode
        )
    }

    suspend fun getCharacters(pageIndex: Int): GetListOfCharacter? {
        val request = NetworkLayer.apolloApiClient.getCharacters(pageIndex)
        return DataTransformUtils.transformCharactersQueryToListOfCharacter(request.body?.characters?.info, request.body?.characters?.results)
    }

    suspend fun getEpisode(id: String): Episode? {
        val request = NetworkLayer.apolloApiClient.getEpisode(id)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return DataTransformUtils.transformEpisodeResponse2ToEpisode(
            response = request.body?.episode,
            character = request.body?.episode?.characters
        )
    }

    suspend fun searchCharacter(pageIndex: Int, filter: FilterCharacter): CharacterPagination? {
        val request = NetworkLayer.apolloApiClient.searchCharacters(pageIndex, filter)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return DataTransformUtils.transformCharacterFilterResponseToCharacters(
            request.body?.characters?.info,
            request.body?.characters?.results
        )
    }

    suspend fun getEpisodes(pageIndex: Int): EpisodePagination? {
        val request = NetworkLayer.apolloApiClient.getEpisodes(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return DataTransformUtils.transformEpisodeResponseToEpisodePaging(request.body?.episodes)
    }
}
