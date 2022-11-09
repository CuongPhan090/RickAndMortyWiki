package com.example.rickandmortywiki.model.networkresponse

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodeByIdResponse(
    val air_date: String? = null,
    val characters: List<String?>? = null,
    val created: String? = null,
    val episode: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val url: String? = null
)
