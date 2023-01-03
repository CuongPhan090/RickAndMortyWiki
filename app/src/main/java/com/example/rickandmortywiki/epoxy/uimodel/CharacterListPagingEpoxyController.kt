package com.example.rickandmortywiki.epoxy.uimodel

import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListBinding
import com.example.rickandmortywiki.databinding.ModelCharacterListTitleBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import kotlinx.coroutines.ObsoleteCoroutinesApi

@OptIn(ObsoleteCoroutinesApi::class)
class CharacterListPagingEpoxyController(
    private val onCharacterClick: (Int) -> Unit
) : PagingDataEpoxyController<CharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: CharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(item, onCharacterClick).id(item?.id)
    }

    // Create different section in the scroll view
    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loadingCharacterList").addTo(this)
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
        val item: CharacterByIdResponse?,
        private val onCharacterClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListBinding>(R.layout.model_character_list) {
        override fun ModelCharacterListBinding.bind() {
            item?.let { character ->
                characterImageView.load(character.image)
                characterNameTextView.text = character.name
                character.id?.let {
                    root.setOnClickListener {
                        onCharacterClick(character.id.toInt())
                    }
                }
            }
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
