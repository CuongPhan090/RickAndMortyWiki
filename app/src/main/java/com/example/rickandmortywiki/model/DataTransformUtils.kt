package com.example.rickandmortywiki.model

import com.example.rickandmortywiki.model.domain.Characters
import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdResponse

object DataTransformUtils {

    fun transformCharacterResponseToCharacter(
        response: CharacterByIdResponse?,
        episode: List<EpisodeByIdResponse>?
    ): Characters? {
        return Characters(
            episode = episode?.map {
                transformEpisodeResponseToEpisode(it)
            },
            gender = response?.gender,
            id = response?.id,
            image = response?.image,
            location = response?.location,
            name = response?.name,
            origin = response?.origin,
            species = response?.species,
            status = response?.status
        )
    }

    fun transformEpisodeResponseToEpisode(response: EpisodeByIdResponse?): Episode {
        return Episode(
            id = response?.id,
            name = response?.name,
            airDate = response?.air_date,
            episode = response?.episode
        )
    }
}
