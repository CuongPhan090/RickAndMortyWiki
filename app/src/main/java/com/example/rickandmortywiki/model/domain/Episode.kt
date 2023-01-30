package com.example.rickandmortywiki.model.domain

data class Episode(
    val id: Int? = null,
    val name: String? = null,
    val airDate: String? = null,
    val season: Int? = null,
    val episode: Int? = null,
    val characters: List<Character>? = emptyList()
) {
    fun getFormattedSeason(): String {
        if (season == null && episode == null) return ""
        return "Season $season Episode $episode"
    }

    fun getFormattedSeasonTruncated(): String {
        if (season == null && episode == null) return ""
        return "S.$season E.$episode"
    }
}
