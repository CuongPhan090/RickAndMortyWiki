package com.example.rickandmortywiki.epoxy.uimodel

import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.data.pagination.CharacterSearchPagingSource
import com.example.rickandmortywiki.databinding.ModelCharacterListBinding
import com.example.rickandmortywiki.databinding.ModelLocalExceptionErrorStateBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Character
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class CharacterSearchEpoxyController(
    private val onCharacterClick: (Int) -> Unit
) : PagingDataEpoxyController<Character>() {

    var localException: CharacterSearchPagingSource.LocalException? = null
        set(value) {
            field = value
            if (field != null) {
                requestForcedModelBuild()
            }
        }

    override fun buildItemModel(currentPosition: Int, item: Character?): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            item, onCharacterClick
        ).id(item?.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (localException != null) {
            ErrorStateEpoxyModel(localException).id("error_state").addTo(this)
            return
        }

        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
        }
        super.addModels(models)
    }

    data class CharacterGridItemEpoxyModel(
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
}
