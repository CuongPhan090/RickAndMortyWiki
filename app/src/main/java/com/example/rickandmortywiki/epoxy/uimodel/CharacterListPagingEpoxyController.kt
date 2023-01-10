package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
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
            CharacterListTitleEpoxyModel(title = null).id("characterListTitleSkeleton").addTo(this)
            super.addModels(
                MutableList(6) {
                    CharacterGridItemEpoxyModel(null, null).id(it)
                }.toList()
            )
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
        private val onCharacterClick: ((Int) -> Unit)?
    ) : ViewBindingKotlinModel<ModelCharacterListBinding>(R.layout.model_character_list) {
        override fun ModelCharacterListBinding.bind() {
            item?.let { character ->
                showShimmerGridItem(show = false, binding = this)
                characterImageView.load(character.image)
                characterNameTextView.text = character.name
                character.id?.let {
                    root.setOnClickListener {
                        onCharacterClick?.let { it1 -> it1(character.id.toInt()) }
                    }
                }
            } ?: run {
                showShimmerGridItem(show = true, binding = this)
            }
        }

        private fun showShimmerGridItem(show: Boolean, binding: ModelCharacterListBinding) {
            if (show) {
                binding.characterNameTextView.visibility = View.GONE
                binding.characterImageView.visibility = View.GONE
                binding.modelCharacterListShimmering.visibility = View.VISIBLE
                binding.modelCharacterListShimmering.startShimmer()
            } else {
                binding.characterNameTextView.visibility = View.VISIBLE
                binding.characterImageView.visibility = View.VISIBLE
                binding.modelCharacterListShimmering.visibility = View.GONE
                binding.modelCharacterListShimmering.stopShimmer()
            }
        }
    }


    data class CharacterListTitleEpoxyModel(
        private val title: String?
    ) : ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            title?.let {
                showShimmerListTitle(show = false, binding = this)
                modelCharacterListTitle.text = title
            } ?: run {
                showShimmerListTitle(show = true, binding = this)
            }
        }

        private fun showShimmerListTitle(show: Boolean, binding: ModelCharacterListTitleBinding) {
            if (show) {
                binding.modelCharacterListTitle.visibility = View.GONE
                binding.modelCharacterListTitleShimmering.visibility = View.VISIBLE
                binding.modelCharacterListTitleShimmering.startShimmer()
            } else {
                binding.modelCharacterListTitle.visibility = View.VISIBLE
                binding.modelCharacterListTitleShimmering.visibility = View.GONE
                binding.modelCharacterListTitleShimmering.stopShimmer()
                binding.root.setCardBackgroundColor(binding.root.resources.getColor(R.color.card_background_color, binding.root.context.theme))
            }
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}
