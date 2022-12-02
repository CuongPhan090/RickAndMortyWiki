package com.example.rickandmortywiki.model.networkresponse

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodeByIdPagingSource(
    val info: Info? = null,
    val results: List<EpisodeByIdResponse>? = null
)
