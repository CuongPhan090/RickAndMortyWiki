package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import coil.load
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterDetailsImageBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

data class ImageEpoxyModel(
    val image: String?
) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
    override fun ModelCharacterDetailsImageBinding.bind() {
        image?.let {
            characterImage.load(image)
            showShimmerImage(false, this)
        } ?: run {
            showShimmerImage(true, this)
        }
    }

    private fun showShimmerImage(show: Boolean, binding: ModelCharacterDetailsImageBinding) {
        binding.apply {
            if (show) {
                modelCharacterDetailsImageShimmer.startShimmer()
                modelCharacterDetailsImageShimmer.visibility = View.VISIBLE
                characterImage.visibility = View.GONE
            } else {
                modelCharacterDetailsImageShimmer.stopShimmer()
                modelCharacterDetailsImageShimmer.visibility = View.GONE
                characterImage.visibility = View.VISIBLE
            }
        }
    }
}
