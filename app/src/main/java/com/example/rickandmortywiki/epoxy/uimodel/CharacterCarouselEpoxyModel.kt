package com.example.rickandmortywiki.epoxy.uimodel

import coil.load
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterCarouselBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

data class CharacterCarouselEpoxyModel(
    val id: Int?,
    val imageUrl: String?,
    val name: String?,
    val characterOnClick: (Int?) -> Any
): ViewBindingKotlinModel<ModelCharacterCarouselBinding>(R.layout.model_character_carousel) {
    override fun ModelCharacterCarouselBinding.bind() {
        characterCarouselImageView.load(imageUrl)
        characterNameTextView.text = name

        root.setOnClickListener {
            characterOnClick(id)
        }
    }
}
