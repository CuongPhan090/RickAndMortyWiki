package com.example.rickandmortywiki.epoxy.uimodel

import android.graphics.BitmapFactory
import android.view.View
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterDetailsHeaderBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Character

data class CharacterHeaderEpoxyModel(
    val character: Character?
) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
    override fun ModelCharacterDetailsHeaderBinding.bind() {
        character?.let {
            showShimmerHeader(false, this)
            characterName.text = it.name
            characterStatus.text = it.status
            if (it.gender?.lowercase() == "male") {
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
        } ?: run {
            showShimmerHeader(true, this)
        }
    }

    private fun showShimmerHeader(show: Boolean, binding: ModelCharacterDetailsHeaderBinding) {
        binding.apply {
            if (show) {
                modelCharacterDetailsHeaderShimmer.startShimmer()
                modelCharacterDetailsHeaderShimmer.visibility = View.VISIBLE
                characterName.visibility = View.GONE
                characterStatus.visibility = View.GONE
                characterGender.visibility = View.GONE
            } else {
                modelCharacterDetailsHeaderShimmer.stopShimmer()
                modelCharacterDetailsHeaderShimmer.visibility = View.GONE
                characterName.visibility = View.VISIBLE
                characterStatus.visibility = View.VISIBLE
                characterGender.visibility = View.VISIBLE
            }
        }
    }
}

