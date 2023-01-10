package com.example.rickandmortywiki.epoxy.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortywiki.epoxy.uimodel.CharacterGridItemEpoxyModel
import com.example.rickandmortywiki.epoxy.uimodel.LongHeaderEpoxyModel
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import kotlinx.coroutines.ObsoleteCoroutinesApi

@OptIn(ObsoleteCoroutinesApi::class)
class CharacterListPagingEpoxyController(
    private val onCharacterClick: (Int) -> Unit
) : PagingDataEpoxyController<CharacterByIdResponse>() {

    var showShimmering = true

    override fun buildItemModel(
        currentPosition: Int,
        item: CharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(item, onCharacterClick).id(item?.id)
    }

    // Create different section in the scroll view
    override fun addModels(models: List<EpoxyModel<*>>) {
        if (showShimmering || models.isEmpty()) {
            LongHeaderEpoxyModel(header = null).id("characterListTitleSkeleton").addTo(this)
            super.addModels(
                MutableList(6) {
                    CharacterGridItemEpoxyModel(null, null).id(it)
                }.toList()
            )
            return
        }

        LongHeaderEpoxyModel("Main Family").id("Main Family").addTo(this)
        super.addModels(models.subList(0, 5))

        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.item?.name?.get(0)?.uppercase()
        }.forEach { mapEntry ->
            val character = mapEntry.key.toString().uppercase()
            LongHeaderEpoxyModel(character).id(character).addTo(this)
            super.addModels(mapEntry.value)
        }
    }
}
