package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import coil.load
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Character

data class CharacterGridItemEpoxyModelSearch(
    val item: Character?,
    private val onCharacterClick: ((Int) -> Unit)?
) : ViewBindingKotlinModel<ModelCharacterListBinding>(R.layout.model_character_list) {
    override fun ModelCharacterListBinding.bind() {
        item?.let { character ->
            showShimmer(false, this)
            characterImageView.load(character.image)
            characterNameTextView.text = character.name
            character.id?.let {
                root.setOnClickListener {
                    onCharacterClick?.let { it1 -> it1(character.id) }
                }
            }
        } ?: run {
            showShimmer(true, this)
        }
    }

    private fun showShimmer(show: Boolean, binding: ModelCharacterListBinding) {
        binding.apply {
            if (show) {
                modelCharacterListShimmering.startShimmer()
                modelCharacterListShimmering.visibility = View.VISIBLE
                characterImageView.visibility = View.GONE
                characterNameTextView.visibility = View.GONE
            } else {
                modelCharacterListShimmering.stopShimmer()
                modelCharacterListShimmering.visibility = View.GONE
                characterImageView.visibility = View.VISIBLE
                characterNameTextView.visibility = View.VISIBLE
            }
        }
    }
}