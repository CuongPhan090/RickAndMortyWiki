package com.example.rickandmortywiki.model.domain

import com.example.rickandmortywiki.model.networkresponse.Location
import com.example.rickandmortywiki.model.networkresponse.Origin

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