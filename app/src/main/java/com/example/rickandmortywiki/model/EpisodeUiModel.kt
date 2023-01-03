package com.example.rickandmortywiki.model

import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.model.domain.EpisodePagination

sealed class EpisodeUiModel {
    class Item(val episode: Episode): EpisodeUiModel()
    class Header(val heading: String): EpisodeUiModel()
}