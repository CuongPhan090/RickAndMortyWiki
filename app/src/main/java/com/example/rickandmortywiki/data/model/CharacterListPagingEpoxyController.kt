package com.example.rickandmortywiki.data.model

import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListBinding
import com.example.rickandmortywiki.databinding.ModelCharacterListTitleBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

class CharacterListPagingEpoxyController : PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(item).id(item?.id)
    }

    // Create different section in the scroll view
    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterListTitleEpoxyModel("Main Family").id("Main Family").addTo(this)
        super.addModels(models.subList(0, 5))

        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.item?.name?.get(0)?.uppercase()
        }.forEach { mapEntry ->
            val character = mapEntry.key.toString().uppercase()
            CharacterListTitleEpoxyModel(character).id(character).addTo(this)

            super.addModels(mapEntry.value)
        }
    }

    data class CharacterGridItemEpoxyModel(
       val item: GetCharacterByIdResponse?
    ) : ViewBindingKotlinModel<ModelCharacterListBinding>(R.layout.model_character_list) {
        override fun ModelCharacterListBinding.bind() {
            characterImageView.load(item?.image)
            characterNameTextView.text = item?.name
        }
    }

    data class CharacterListTitleEpoxyModel(
        private val title: String
    ) : ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            modelCharacterListTitle.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}