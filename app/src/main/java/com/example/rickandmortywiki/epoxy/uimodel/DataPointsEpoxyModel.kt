package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterDetailsDataPointBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

data class DataPointsEpoxyModel(
    val title: String?,
    val description: String?
) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
    override fun ModelCharacterDetailsDataPointBinding.bind() {
        description?.let {
            showShimmerDataPoints(false, this)
            label.text = title
            context.text = description
        } ?: showShimmerDataPoints(true, this)
    }

    private fun showShimmerDataPoints(
        show: Boolean,
        binding: ModelCharacterDetailsDataPointBinding
    ) {
        binding.apply {
            if (show) {
                modelCharacterDetailsDataPointShimmer.startShimmer()
                modelCharacterDetailsDataPointShimmer.visibility = View.VISIBLE
                label.visibility = View.GONE
                context.visibility = View.GONE
            } else {
                modelCharacterDetailsDataPointShimmer.stopShimmer()
                modelCharacterDetailsDataPointShimmer.visibility = View.GONE
                label.visibility = View.VISIBLE
                context.visibility = View.VISIBLE

            }
        }
    }
}
