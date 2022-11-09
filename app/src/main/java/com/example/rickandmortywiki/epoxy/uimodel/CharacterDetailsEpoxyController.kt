package com.example.rickandmortywiki.epoxy.uimodel

import android.graphics.BitmapFactory
import coil.load
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterDetailsDataPointBinding
import com.example.rickandmortywiki.databinding.ModelCharacterDetailsHeaderBinding
import com.example.rickandmortywiki.databinding.ModelCharacterDetailsImageBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Character

class CharacterDetailsEpoxyController : EpoxyController() {
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
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

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

        // add data points model
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
    ): ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            label.text = title
            context.text = description
        }
    }
}
