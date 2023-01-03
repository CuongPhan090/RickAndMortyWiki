package com.example.rickandmortywiki.epoxy.uimodel

import coil.load
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListSquareBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Character

class EpisodeDetailsEpoxyController(private val listOfCharacters: List<Character>?): EpoxyController() {
    override fun buildModels() {
        listOfCharacters?.forEach {
            CharacterEpoxyModel(it.image, it.name).id("${it.image}_${it.name}").addTo(this)
        }
    }

    data class CharacterEpoxyModel(
        val imageUrl: String?,
        val name: String?
    ): ViewBindingKotlinModel<ModelCharacterListSquareBinding>(R.layout.model_character_list_square) {
        override fun ModelCharacterListSquareBinding.bind() {
            characterImageView.load(imageUrl)
            characterNameTextView.text = name
        }
    }
}
