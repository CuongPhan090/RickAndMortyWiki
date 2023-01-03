package com.example.rickandmortywiki.model.domain

data class Episode(
    val id: Int? = null,
    val name: String? = null,
    val airDate: String? = null,
    val season: Int? = null,
    val episode: Int? = null,
    val characters: List<Characters>? = emptyList()
) {
    fun getFormattedSeason(): String {
        return "Season $season Episode $episode"
    }

    fun getFormattedSeasonTruncated(): String {
        return "S.$season E.$episode"
    }
}
