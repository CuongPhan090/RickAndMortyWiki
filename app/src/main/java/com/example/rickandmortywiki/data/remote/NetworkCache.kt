package com.example.rickandmortywiki.data.remote

import com.example.rickandmortywiki.model.domain.Character

object NetworkCache {
    val characterResponseMap = mutableMapOf<Int, Character?>()
}