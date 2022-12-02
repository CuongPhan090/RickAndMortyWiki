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
            season = getSeasonFromEpisodeString(response?.episode),
            episode = getEpisodeFromEpisodeString(response?.episode)
        )
    }

    private fun getSeasonFromEpisodeString(episodeString: String?): Int {
        episodeString?.let { episode ->
            val seasonIndex = episode.indexOfFirst { it.equals('s', true) }
            if (seasonIndex == -1) {
                return 0
            }
            return episode.substring(seasonIndex + 1, seasonIndex + 3).toIntOrNull() ?: 0
        } ?: return 0
    }

    private fun getEpisodeFromEpisodeString(episodeString : String?): Int {
        episodeString?.let { episode ->
            val episodeIndex = episode.indexOfFirst { it.equals('e', true) }
            if (episodeIndex == -1) {
                return 0
            }
            return episode.substring(episodeIndex + 1, episodeIndex + 3).toIntOrNull() ?: 0
        } ?: return 0
    }
}
