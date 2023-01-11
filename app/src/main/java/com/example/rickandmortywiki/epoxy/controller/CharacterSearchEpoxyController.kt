package com.example.rickandmortywiki.epoxy.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortywiki.data.pagination.CharacterSearchPagingSource
import com.example.rickandmortywiki.epoxy.uimodel.CharacterGridItemEpoxyModel
import com.example.rickandmortywiki.epoxy.uimodel.CharacterGridItemEpoxyModelSearch
import com.example.rickandmortywiki.epoxy.uimodel.ErrorStateEpoxyModel
import com.example.rickandmortywiki.model.domain.Character
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class CharacterSearchEpoxyController(
    private val onCharacterClick: (Int) -> Unit
) : PagingDataEpoxyController<Character>() {

    var shimmering: Boolean = true
    var localException: CharacterSearchPagingSource.LocalException? = null
        set(value) {
            field = value
            if (field != null) {
                requestForcedModelBuild()
            }
        }

    override fun buildItemModel(currentPosition: Int, item: Character?): EpoxyModel<*> {
        return CharacterGridItemEpoxyModelSearch(
            item, onCharacterClick
        ).id(item?.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (localException != null) {
            ErrorStateEpoxyModel(localException).id("error_state").addTo(this)
            return
        }

        if (shimmering) {
            MutableList(8) {
                CharacterGridItemEpoxyModel(null, null).id(it).addTo(this)
                return
            }
        }

        super.addModels(models)
    }
}

