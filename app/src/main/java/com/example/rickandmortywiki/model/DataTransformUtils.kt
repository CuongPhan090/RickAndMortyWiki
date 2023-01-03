package com.example.rickandmortywiki.model

import com.example.rickandmortywiki.*
import com.example.rickandmortywiki.model.domain.*
import com.example.rickandmortywiki.model.networkresponse.*

object DataTransformUtils {

    fun transformCharacterResponseToCharacter(
        response: CharacterByIdResponse?,
        episode: List<CharacterQuery.Episode?>? = null
    ): Character {
        return Character(
            episode = episode?.map {
                transformEpisodeResponse1ToEpisode(it)
            },
            gender = response?.gender,
           // id = response?.id,
            image = response?.image,
            location = response?.location,
            name = response?.name,
            origin = response?.origin,
            species = response?.species,
            status = response?.status
        )
    }

    fun transformCharactersQueryToListOfCharacter(
        charactersQueryInfo: CharactersQuery.Info?,
        charactersQueryResult: List<CharactersQuery.Result?>?,
    ): GetListOfCharacter {
        return GetListOfCharacter(
            info = Info(
                next = charactersQueryInfo?.next,
                pages = charactersQueryInfo?.pages,
                prev = charactersQueryInfo?.prev
            ),
            results = charactersQueryResult?.map {
                CharacterByIdResponse(name = it?.name, image = it?.image, id = it?.id)
            }
        )
    }

    fun transformCharacterResponse1ToCharacter(
        response: CharacterQuery.Character?,
        episode: List<CharacterQuery.Episode?>? = null
    ): Character {
        return Character(
            episode = episode?.map {
                transformEpisodeResponseToEpisode(it)
            },
            gender = response?.gender,
            image = response?.image,
            name = response?.name,
            origin = Origin(
                response?.origin?.name),
            species = response?.species,
            status = response?.status
        )
    }

    fun transformEpisodeResponseToEpisode(
        response: CharacterQuery.Episode?,
        character: List<CharacterByIdResponse>? = emptyList()
    ): Episode {
        return Episode(
            id = response?.id?.toInt(),
            name = response?.name,
            airDate = response?.air_date,
            season = getSeasonFromEpisodeString(response?.episode),
            episode = getEpisodeFromEpisodeString(response?.episode),
            characters = character?.map {
                transformCharacterResponseToCharacter(it)
            }
        )
    }

    fun transformEpisodeResponse2ToEpisode(
        response: EpisodeDetailsQuery.Episode?,
        character: List<EpisodeDetailsQuery.Character?>? = emptyList()
    ): Episode {
        return Episode(
            id = response?.id?.toInt(),
            name = response?.name,
            airDate = response?.air_date,
            season = getSeasonFromEpisodeString(response?.episode),
            episode = getEpisodeFromEpisodeString(response?.episode),
            characters = character?.map {
                Character(
                    id = it?.id?.toInt(),
                    name = it?.name,
                    image = it?.image
                )
            }
        )
    }

    fun transformEpisodeResponseToEpisodePaging(
        episodesQuery: EpisodesQuery.Episodes?
    ): EpisodePagination {
        val info = episodesQuery?.info
        val episode = episodesQuery?.results

        return EpisodePagination(
            info = Infor(
                pages = info?.pages,
                next = info?.next,
                prev = info?.prev
            ),
            episode = episode?.map {
                Episode(
                    id = it?.id?.toInt(),
                    name = it?.name,
                    airDate = it?.air_date,
                    season = getSeasonFromEpisodeString(it?.episode),
                    episode = getEpisodeFromEpisodeString(it?.episode)
                )
            }
        )
    }

    fun transformEpisodeResponse1ToEpisode(
        response: CharacterQuery.Episode?,
        character: List<CharacterByIdResponse>? = emptyList()
    ): Episode {
        return Episode(
            name = response?.name,
            airDate = response?.air_date,
            season = getSeasonFromEpisodeString(response?.episode),
            episode = getEpisodeFromEpisodeString(response?.episode),
            characters = character?.map {
                transformCharacterResponseToCharacter(it)
            }
        )
    }

    fun transformCharacterFilterResponseToCharacters(
        infor: CharacterSearchQuery.Info?,
        characters: List<CharacterSearchQuery.Result?>?
    ): CharacterPagination {
        return CharacterPagination(
            info = Infor(
                next = infor?.next,
                pages = infor?.pages,
                prev = infor?.prev
            ),
            characters = characters?.map {
                Character(
                    id = it?.id?.toInt(),
                    name = it?.name,
                    image = it?.image
                )
            } ?: emptyList()
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

    private fun getEpisodeFromEpisodeString(episodeString: String?): Int {
        episodeString?.let { episode ->
            val episodeIndex = episode.indexOfFirst { it.equals('e', true) }
            if (episodeIndex == -1) {
                return 0
            }
            return episode.substring(episodeIndex + 1, episodeIndex + 3).toIntOrNull() ?: 0
        } ?: return 0
    }
}
