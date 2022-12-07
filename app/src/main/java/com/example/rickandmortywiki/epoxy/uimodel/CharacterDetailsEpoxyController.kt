package com.example.rickandmortywiki.epoxy.uimodel

import android.graphics.BitmapFactory
import android.util.Log
import coil.load
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.*
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Characters
import com.example.rickandmortywiki.model.domain.Episode

class CharacterDetailsEpoxyController(val onClickEpisode: (Int?) -> Unit) : EpoxyController() {
    // if data is being fetched, display progress bar
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    // remove progress bar once data has been fetched
    var charactersResponse: Characters? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    // called when UI update requested
    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if (charactersResponse == null) {
            // TODO: Handle error
            return
        }

        // add header model
        HeaderEpoxyModel(
            name = charactersResponse?.name,
            gender = charactersResponse?.gender,
            status = charactersResponse?.status
        ).id("header").addTo(this)

        // add image model
        ImageEpoxyModel(
            image = charactersResponse?.image
        ).id("image").addTo(this)

        // add episode carousel item
        if (charactersResponse?.episode?.isNotEmpty() == true) {
            val listOfEpisode = charactersResponse?.episode?.map {
                EpisodeCarouselEpoxyModel(it,onClickEpisode).id(it.id)
            }

            EpisodeHeader(headerText = "Episodes").id("episode_header").addTo(this)

            listOfEpisode?.let {
                CarouselModel_()
                    .id("episode_carousel")
                    .models(it)
                    .numViewsToShowOnScreen(1.25f)
                    .addTo(this)
            }
        }

        // add data points model
        DataPointsEpoxyModel(
            title = "Origin",
            description = charactersResponse?.origin?.name
        ).id("origin").addTo(this)

        DataPointsEpoxyModel(
            title = "Specie",
            description = charactersResponse?.species
        ).id("specie").addTo(this)


    }

    data class HeaderEpoxyModel(
        val name: String?,
        val gender: String?,
        val status: String?
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            characterName.text = name
            characterStatus.text = status
            if (gender?.lowercase() == "male") {
                characterGender.setImageBitmap(
                    BitmapFactory.decodeResource(
                        root.resources,
                        R.drawable.male_symbol
                    )
                )
            } else {
                characterGender.setImageBitmap(
                    BitmapFactory.decodeResource(
                        root.resources,
                        R.drawable.female_symbol
                    )
                )
            }
        }
    }

    data class ImageEpoxyModel(
        val image: String?
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            characterImage.load(image)
        }
    }

    data class DataPointsEpoxyModel(
        val title: String?,
        val description: String?
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            label.text = title
            context.text = description
        }
    }

    data class EpisodeCarouselEpoxyModel(
        val episode: Episode,
        val onClick: (Int?) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeCarouselItemsBinding>(R.layout.model_episode_carousel_items) {
        override fun ModelEpisodeCarouselItemsBinding.bind() {
            episodeSeason.text = episode.getFormattedSeasonTruncated()
            episodeName.text = episode.name
            episodeAirDay.text = episode.airDate
            root.setOnClickListener {
                onClick(episode.id)
            }
        }
    }

    data class EpisodeHeader(val headerText: String) :
        ViewBindingKotlinModel<ModelHeaderBinding>(R.layout.model_header) {
        override fun ModelHeaderBinding.bind() {
            header.text = headerText
        }
    }
}
