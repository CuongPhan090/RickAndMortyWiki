package com.example.rickandmortywiki.epoxy.controller

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortywiki.epoxy.uimodel.*
import com.example.rickandmortywiki.model.domain.Character
import com.example.rickandmortywiki.util.NumberUtils.toPx

class CharacterDetailsEpoxyController(private val onClickEpisode: (Int?) -> Unit) : EpoxyController() {
    // if data is being fetched, display progress bar
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    // remove progress bar once data has been fetched
    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    // called when UI update requested
    override fun buildModels() {
        // add episode carousel item
        character?.name?.let {
            CharacterHeaderEpoxyModel(character).id("character_header").addTo(this)
            ImageEpoxyModel(character?.image).id("character_image").addTo(this)


            if (character?.episode?.isNotEmpty() == true) {
                val listOfEpisode = character?.episode?.map { episode ->
                    EpisodeCarouselEpoxyModel(episode, onClickEpisode).id(episode.id)
                }

                ShortHeaderEpoxyModel(headerText = "Episodes").id("episode_header").addTo(this)

                listOfEpisode?.let { list ->
                    CarouselModel_()
                        .id("episode_carousel")
                        .models(list)
                        .padding(Carousel.Padding(8.toPx, 0, 8.toPx, 0, 0))
                        .numViewsToShowOnScreen(1.25f)
                        .addTo(this)
                }
            }
        } ?: run {
            // shimmering view
            CharacterHeaderEpoxyModel(null).id("character_header").addTo(this)
            ImageEpoxyModel(null).id("character_image").addTo(this)

            ShortHeaderEpoxyModel(headerText = null).id("episodes").addTo(this)

            CarouselModel_()
                .id("episode_carousel")
                .models(
                    listOf(
                        EpisodeCarouselEpoxyModel(null, onClickEpisode).id("episode_carousel_1"),
                        EpisodeCarouselEpoxyModel(null, onClickEpisode).id("episode_carousel_2")
                    )
                )
                .padding(Carousel.Padding(8.toPx, 0, 8.toPx, 0, 0))
                .numViewsToShowOnScreen(1.25f)
                .addTo(this)
        }

        DataPointsEpoxyModel(
            title = "Origin",
            description = character?.origin?.name
        ).id("origin").addTo(this)

        DataPointsEpoxyModel(
            title = "Specie",
            description = character?.species
        ).id("specie").addTo(this)
    }
}
