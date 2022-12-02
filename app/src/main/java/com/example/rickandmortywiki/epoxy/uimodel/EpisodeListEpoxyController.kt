package com.example.rickandmortywiki.epoxy.uimodel

import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelEpisodeListItemBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Episode
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class EpisodeListEpoxyController(
    private val onEpisodeClick: (Int) -> Unit
): PagingDataEpoxyController<Episode>() {
    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        return EpisodeListItemEpoxyModel(
            item,
            onEpisodeClick
        ).id("episode_${item?.id}")
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode?,
        val onClick: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {
        override fun ModelEpisodeListItemBinding.bind() {
            Log.d("episodeProxyModel", "ModelEpisodeListItemBinding")
            Log.d("episodeProxyModel", "$episode")
            episodeName.text = episode?.name
            episodeNumber.text = episode?.episode
            episodeAirDay.text = episode?.airDate
//            episodeListItemCardView.setOnClickListener{
//                episode?.id?.let { id ->
//                    onClick(id)
//                }
//            }
        }
    }
}