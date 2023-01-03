package com.example.rickandmortywiki.data.remote

import com.example.rickandmortywiki.model.domain.Characters

object NetworkCache {
    val characterResponseMap = mutableMapOf<Int, Characters?>()
}