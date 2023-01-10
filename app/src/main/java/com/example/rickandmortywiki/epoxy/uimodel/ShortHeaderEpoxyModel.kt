package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelShortHeaderBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

data class ShortHeaderEpoxyModel(val headerText: String?) :
    ViewBindingKotlinModel<ModelShortHeaderBinding>(R.layout.model_short_header) {
    override fun ModelShortHeaderBinding.bind() {
        headerText?.let {
            showShimmerHeader(false, this)
            header.text = headerText
        } ?: run {
            showShimmerHeader(true, this)
        }
    }

    private fun showShimmerHeader(show: Boolean, binding: ModelShortHeaderBinding) {
        binding.apply {
            if (show) {
                modelHeaderShimmer.startShimmer()
                modelHeaderShimmer.visibility = View.VISIBLE
                header.visibility = View.GONE
            } else {
                modelHeaderShimmer.stopShimmer()
                modelHeaderShimmer.visibility = View.GONE
                header.visibility = View.VISIBLE
            }
        }
    }
}
