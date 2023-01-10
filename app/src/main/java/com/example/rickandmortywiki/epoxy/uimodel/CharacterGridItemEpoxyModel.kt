package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import coil.load
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse

data class CharacterGridItemEpoxyModel(
    val item: CharacterByIdResponse?,
    private val onCharacterClick: ((Int) -> Unit)?
) : ViewBindingKotlinModel<ModelCharacterListBinding>(R.layout.model_character_list) {
    override fun ModelCharacterListBinding.bind() {
        item?.let { character ->
            showShimmerGridItem(show = false, binding = this)
            characterImageView.load(character.image)
            characterNameTextView.text = character.name
            character.id?.let {
                root.setOnClickListener {
                    onCharacterClick?.let { it1 -> it1(character.id.toInt()) }
                }
            }
        } ?: run {
            showShimmerGridItem(show = true, binding = this)
        }
    }

    private fun showShimmerGridItem(show: Boolean, binding: ModelCharacterListBinding) {
        binding.apply {
            if (show) {
                characterNameTextView.visibility = View.GONE
                characterImageView.visibility = View.GONE
                modelCharacterListShimmering.visibility = View.VISIBLE
                modelCharacterListShimmering.startShimmer()
            } else {
                characterNameTextView.visibility = View.VISIBLE
                characterImageView.visibility = View.VISIBLE
                modelCharacterListShimmering.visibility = View.GONE
                modelCharacterListShimmering.stopShimmer()
            }
        }
    }
}
