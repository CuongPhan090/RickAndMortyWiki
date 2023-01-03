package com.example.rickandmortywiki.epoxy.uimodel

import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import coil.load
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.*
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Character
import com.example.rickandmortywiki.model.domain.Episode
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
    var characterResponse: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    // called when UI update requested
    override fun buildModels() {
        if (characterResponse == null) {
            // TODO: Handle error
            return
        }

        // add header model
        HeaderEpoxyModel(
            name = characterResponse?.name,
            gender = characterResponse?.gender,
            status = characterResponse?.status
        ).id("header").addTo(this)

        // add image model
        ImageEpoxyModel(
            image = characterResponse?.image
        ).id("image").addTo(this)

        // add episode carousel item
        characterResponse?.episode?.let { episodes ->
            if (episodes.isNotEmpty()) {
                val listOfEpisode = characterResponse?.episode?.map {
                    EpisodeCarouselEpoxyModel(it, onClickEpisode).id(it.id)
                }

                EpisodeHeader(headerText = "Episodes").id("episode_header").addTo(this)

                listOfEpisode?.let {
                    CarouselModel_()
                        .id("episode_carousel")
                        .models(it)
                        .padding(Carousel.Padding(8.toPx, 0, 8.toPx, 0, 0))
                        .numViewsToShowOnScreen(1.25f)
                        .addTo(this)
                }
            }
        } ?: run {
            // shimmering view
            EpisodeHeader(headerText = null).id("episodes").addTo(this)

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
            description = characterResponse?.origin?.name
        ).id("origin").addTo(this)

        DataPointsEpoxyModel(
            title = "Specie",
            description = characterResponse?.species
        ).id("specie").addTo(this)
    }

    data class HeaderEpoxyModel(
        val name: String?,
        val gender: String?,
        val status: String?
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            if (name == null && gender == null && status == null) {
                showShimmerHeader(true, this)
                return
            }

            showShimmerHeader(false, this)
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

        private fun showShimmerHeader(show: Boolean, binding: ModelCharacterDetailsHeaderBinding) {
            if (show) {
                binding.apply {
                    modelCharacterDetailsHeaderShimmer.startShimmer()
                    modelCharacterDetailsHeaderShimmer.visibility = View.VISIBLE
                    characterName.visibility = View.GONE
                    characterStatus.visibility = View.GONE
                    characterGender.visibility = View.GONE
                }
            } else {
                binding.apply {
                    modelCharacterDetailsHeaderShimmer.stopShimmer()
                    modelCharacterDetailsHeaderShimmer.visibility = View.GONE
                    characterName.visibility = View.VISIBLE
                    characterStatus.visibility = View.VISIBLE
                    characterGender.visibility = View.VISIBLE
                }
            }
        }
    }


    data class ImageEpoxyModel(
        val image: String?
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            if (image == null) {
                showShimmerImage(true, this)
                return
            }
            characterImage.load(image)
            showShimmerImage(false, this)
        }

        private fun showShimmerImage(show: Boolean, binding: ModelCharacterDetailsImageBinding) {
            if (show) {
                binding.apply {
                    modelCharacterDetailsImageShimmer.startShimmer()
                    modelCharacterDetailsImageShimmer.visibility = View.VISIBLE
                    characterImage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    modelCharacterDetailsImageShimmer.stopShimmer()
                    modelCharacterDetailsImageShimmer.visibility = View.GONE
                    characterImage.visibility = View.VISIBLE
                }
            }
        }
    }

    data class DataPointsEpoxyModel(
        val title: String?,
        val description: String?
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            if (description == null) {
                showShimmerDataPoints(true, this)
                return
            }
            showShimmerDataPoints(false, this)
            label.text = title
            context.text = description
        }

        private fun showShimmerDataPoints(
            show: Boolean,
            binding: ModelCharacterDetailsDataPointBinding
        ) {
            if (show) {
                binding.apply {
                    modelCharacterDetailsDataPointShimmer.startShimmer()
                    modelCharacterDetailsDataPointShimmer.visibility = View.VISIBLE
                    label.visibility = View.GONE
                    context.visibility = View.GONE
                }
            } else {
                binding.apply {
                    modelCharacterDetailsDataPointShimmer.stopShimmer()
                    modelCharacterDetailsDataPointShimmer.visibility = View.GONE
                    label.visibility = View.VISIBLE
                    context.visibility = View.VISIBLE
                }
            }
        }
    }

    data class EpisodeCarouselEpoxyModel(
        val episode: Episode?,
        val onClick: (Int?) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeCarouselItemsBinding>(R.layout.model_episode_carousel_items) {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun ModelEpisodeCarouselItemsBinding.bind() {
            episode?.let { episodeDetails ->
                showShimmerEpisodeCarousel(false, this)
                episodeName.text = episodeDetails.name
                episodeAirDay.text = episodeDetails.airDate
                episodeSeason.text = episodeDetails.getFormattedSeasonTruncated()
                root.setOnClickListener {
                    onClick(episodeDetails.id)
                }
            } ?: run {
                showShimmerEpisodeCarousel(true, this)
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        private fun showShimmerEpisodeCarousel(
            show: Boolean,
            binding: ModelEpisodeCarouselItemsBinding
        ) {
            if (show) {
                binding.apply {
                    modelEpisodeCarouselItemsShimmer.startShimmer()
                    modelEpisodeCarouselItemsShimmer.visibility = View.VISIBLE
                    episodeName.visibility = View.GONE
                    episodeAirDay.visibility = View.GONE
                    episodeSeason.visibility = View.GONE
                    binding.root.strokeColor =
                        root.resources.getColor(R.color.shimmer_view_background, root.context.theme)
                }
            } else {
                binding.apply {
                    modelEpisodeCarouselItemsShimmer.stopShimmer()
                    modelEpisodeCarouselItemsShimmer.visibility = View.GONE
                    episodeName.visibility = View.VISIBLE
                    episodeAirDay.visibility = View.VISIBLE
                    episodeSeason.visibility = View.VISIBLE
                    binding.root.strokeColor =
                        root.resources.getColor(R.color.black, root.context.theme)
                }
            }
        }
    }

    data class EpisodeHeader(val headerText: String?) :
        ViewBindingKotlinModel<ModelHeaderBinding>(R.layout.model_header) {
        override fun ModelHeaderBinding.bind() {
            if (headerText == null) {
                showShimmerHeader(true, this)
                return
            }
            showShimmerHeader(false, this)
            header.text = headerText
        }

        private fun showShimmerHeader(show: Boolean, binding: ModelHeaderBinding) {
            if (show) {
                binding.apply {
                    modelHeaderShimmer.startShimmer()
                    modelHeaderShimmer.visibility = View.VISIBLE
                    header.visibility = View.GONE
                }
            } else {
                binding.apply {
                    modelHeaderShimmer.stopShimmer()
                    modelHeaderShimmer.visibility = View.GONE
                    header.visibility = View.VISIBLE
                }
            }
        }
    }
}
