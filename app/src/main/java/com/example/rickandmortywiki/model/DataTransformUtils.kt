package com.example.rickandmortywiki.model

object DataTransformUtils {

    fun transformToCharacter(response: CharacterByIdResponse?): Character? {
        return Character(
            episode = emptyList(),
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
}