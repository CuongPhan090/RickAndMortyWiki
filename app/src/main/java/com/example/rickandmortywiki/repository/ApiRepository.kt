package com.example.rickandmortywiki.repository

import com.example.rickandmortywiki.data.Remote

class ApiRepository {
    private val remote = Remote()

    suspend fun getCharacterById(character: Int) = remote.getCharacterById(character)
}