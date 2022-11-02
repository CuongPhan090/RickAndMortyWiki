package com.example.rickandmortywiki.data.remote

class ClientApi(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun getCharacterById(character: Int) = rickAndMortyService.getCharacterById(character)
}
