package com.example.rickandmortywiki.model

import com.example.rickandmortywiki.model.domain.Episode

sealed class EpisodeUiModel {
    class Item(val episode: Episode): EpisodeUiModel()
    class Header(val heading: String): EpisodeUiModel()
}