package com.example.rickandmortywiki.epoxy.uimodel

import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelLoadingBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

class LoadingEpoxyModel: ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading) {
    override fun ModelLoadingBinding.bind() {
        // TODO: nothing
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}
