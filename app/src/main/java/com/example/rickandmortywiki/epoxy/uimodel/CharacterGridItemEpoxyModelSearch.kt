package com.example.rickandmortywiki.epoxy.uimodel

import coil.load
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Character

data class CharacterGridItemEpoxyModelSearch(
    val item: Character?,
    private val onCharacterClick: (Int) -> Unit
) : ViewBindingKotlinModel<ModelCharacterListBinding>(R.layout.model_character_list) {
    override fun ModelCharacterListBinding.bind() {
        item?.let { character ->
            characterImageView.load(character.image)
            characterNameTextView.text = character.name
            character.id?.let {
                root.setOnClickListener {
                    onCharacterClick(character.id)
                }
            }
        }
    }
}