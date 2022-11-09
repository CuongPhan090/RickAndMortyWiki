package com.example.rickandmortywiki.model

data class Character(
    val episode: List<Episode>? = null,
    val gender: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val location: Location? = null,
    val name: String? = null,
    val origin: Origin? = null,
    val species: String? = null,
    val status: String? = null,
)

data class Episode(
    val id: Int? = null,
    val name: String? = null
)