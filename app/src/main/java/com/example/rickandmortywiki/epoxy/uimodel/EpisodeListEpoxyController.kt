package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelCharacterListTitleBinding
import com.example.rickandmortywiki.databinding.ModelEpisodeListItemBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.EpisodeUiModel
import com.example.rickandmortywiki.model.domain.Episode
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class EpisodeListEpoxyController(
    private val onEpisodeClick: (Int) -> Unit
): PagingDataEpoxyController<EpisodeUiModel>() {

    var showShimmering = true
    override fun buildItemModel(currentPosition: Int, item: EpisodeUiModel?): EpoxyModel<*> {
        return when (item!!) {
            is EpisodeUiModel.Item -> {
                val episode = (item as? EpisodeUiModel.Item)?.episode
                EpisodeListItemEpoxyModel(
                    episode,
                    onEpisodeClick
                ).id("episode_${episode?.id}")
            }
            is EpisodeUiModel.Header -> {
                val header = (item as? EpisodeUiModel.Header)?.heading
                EpisodeListTitleEpoxyModel(header).id("header_${header}")
            }
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (showShimmering) {
            EpisodeListTitleEpoxyModel(headerText = null).id("header_shimmering").addTo(this)
            repeat(6) {
                EpisodeListItemEpoxyModel(episode = null, null).id(it).addTo(this)
            }
            return
        }
        super.addModels(models)
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode?,
        val onClick: ((Int) -> Unit)?
    ): ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {
        override fun ModelEpisodeListItemBinding.bind() {
            episode?.let {
                episodeListItemEpoxyModelShimmering(false, this)
                episodeName.text = it.name
                episodeNumber.text = it.getFormattedSeasonTruncated()
                episodeAirDay.text = it.airDate
                it.id?.let { id ->
                    root.setOnClickListener {
                        onClick?.let { it1 -> it1(id) }
                    }
                }

            } ?: run {
                episodeListItemEpoxyModelShimmering(true, this)
            }

        }

        private fun episodeListItemEpoxyModelShimmering(show: Boolean, binding: ModelEpisodeListItemBinding) {
            if (show) {
                binding.apply {
                    modelEpisodeListItemShimmering.startShimmer()
                    modelEpisodeListItemShimmering.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    root.isEnabled = true
                    modelEpisodeListItemShimmering.visibility = View.GONE
                    modelEpisodeListItemShimmering.stopShimmer()
                    episodeNumber.background =
                        ResourcesCompat.getDrawable(root.resources, R.drawable.background_episode_number, root.context.theme)
                    episodeListItemCardView.strokeColor = root.resources.getColor(R.color.black, root.context.theme)
                }
            }
        }
    }

    data class EpisodeListTitleEpoxyModel(val headerText: String?): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            headerText?.let {
                episodeListTitleEpoxyModelShimmering(false, this)
                modelCharacterListTitle.text = headerText
            } ?: run {
                episodeListTitleEpoxyModelShimmering(true, this)
            }
        }

        private fun episodeListTitleEpoxyModelShimmering(show: Boolean, binding: ModelCharacterListTitleBinding) {
            if (show) {
                binding.modelCharacterListTitleShimmering.visibility = View.VISIBLE
                binding.modelCharacterListTitleShimmering.startShimmer()
                binding.modelCharacterListTitle.visibility = View.GONE
            } else {
                binding.modelCharacterListTitleShimmering.visibility = View.GONE
                binding.modelCharacterListTitleShimmering.stopShimmer()
                binding.root.setCardBackgroundColor(binding.root.resources.getColor(R.color.card_background_color, binding.root.context.theme))
                binding.modelCharacterListTitle.visibility = View.VISIBLE
            }
        }
    }
}