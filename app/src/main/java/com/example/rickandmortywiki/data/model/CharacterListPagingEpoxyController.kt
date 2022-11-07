package com.example.rickandmortywiki.data.model

import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel

class CharacterListPagingEpoxyController : PagedListEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGriItemEpoxyModel(item?.image, item?.name).id(item?.id)
    }

    data class CharacterGriItemEpoxyModel(
        val characterImage: String?,
        val characterName: String?
    ) : ViewBindingKotlinModel<ModelCharacterListBinding>(R.layout.model_character_list) {
        override fun ModelCharacterListBinding.bind() {
            characterImageView.load(characterImage)
            characterNameTextView.text = characterName
        }
    }
}