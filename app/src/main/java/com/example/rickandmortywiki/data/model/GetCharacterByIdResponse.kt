package com.example.rickandmortywiki.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetListOfCharacter(
    val info: Info? = null,
    val results: List<GetCharacterByIdResponse>? = null
)

@JsonClass(generateAdapter = true)
data class Info(
    val count: Int? = 0,
    val next: String? = null,
    val pages: Int? = 0,
    val prev: Any?= null
)

@JsonClass(generateAdapter = true)
@Json(name = "Result")
data class GetCharacterByIdResponse(
    val created: String? = null,
    val episode: List<String>? = null,
    val gender: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val location: Location? = null,
    val name: String? = null,
    val origin: Origin? = null,
    val species: String? = null,
    val status: String? = null,
    val type: String? = null,
    val url: String? = null
)

@JsonClass(generateAdapter = true)
data class Location(
    val name: String? = null,
    val url: String? = null
)

@JsonClass(generateAdapter = true)
data class Origin(
    val name: String? = null,
    val url: String? = null,
)