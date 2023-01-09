package com.example.rickandmortywiki.epoxy.uimodel

import android.util.Log
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

    data class EpisodeListItemEpoxyModel(
        val episode: Episode?,
        val onClick: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {
        override fun ModelEpisodeListItemBinding.bind() {
            Log.d("episodeProxyModel", "ModelEpisodeListItemBinding")
            Log.d("episodeProxyModel", "$episode")
            episodeName.text = episode?.name
            episodeNumber.text = episode?.getFormattedSeasonTruncated()
            episodeAirDay.text = episode?.airDate
            episodeListItemCardView.setOnClickListener{
                episode?.id?.let { id ->
                    onClick(id)
                }
            }
        }
    }

    data class EpisodeListTitleEpoxyModel(val headerText: String?): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            modelCharacterListTitle.text = headerText
        }
    }
}