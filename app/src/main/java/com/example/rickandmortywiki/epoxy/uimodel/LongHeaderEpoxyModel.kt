package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelLongHeaderBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

data class LongHeaderEpoxyModel(
    private val header: String?
) : ViewBindingKotlinModel<ModelLongHeaderBinding>(R.layout.model_long_header) {
    override fun ModelLongHeaderBinding.bind() {
        header?.let {
            showShimmerListTitle(show = false, binding = this)
            modelCharacterListTitle.text = header
        } ?: run {
            showShimmerListTitle(show = true, binding = this)
        }
    }

    private fun showShimmerListTitle(show: Boolean, binding: ModelLongHeaderBinding) {
        binding.apply {
            if (show) {
                modelCharacterListTitle.visibility = View.GONE
                modelCharacterListTitleShimmering.visibility = View.VISIBLE
                modelCharacterListTitleShimmering.startShimmer()
            } else {
                modelCharacterListTitle.visibility = View.VISIBLE
                modelCharacterListTitleShimmering.visibility = View.GONE
                modelCharacterListTitleShimmering.stopShimmer()
                root.setCardBackgroundColor(binding.root.resources.getColor(R.color.card_background_color, binding.root.context.theme))
            }
        }
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}
