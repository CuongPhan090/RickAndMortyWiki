package com.example.rickandmortywiki.epoxy.uimodel

import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.pagination.CharacterSearchPagingSource
import com.example.rickandmortywiki.databinding.ModelLocalExceptionErrorStateBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

data class ErrorStateEpoxyModel(
    val localException: CharacterSearchPagingSource.LocalException?
) : ViewBindingKotlinModel<ModelLocalExceptionErrorStateBinding>(R.layout.model_local_exception_error_state) {
    override fun ModelLocalExceptionErrorStateBinding.bind() {
        errorTitle.text = localException?.title
        errorDescription.text = localException?.description
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}
