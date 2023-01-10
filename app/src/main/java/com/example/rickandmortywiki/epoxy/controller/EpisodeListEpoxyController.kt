package com.example.rickandmortywiki.epoxy.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortywiki.epoxy.uimodel.EpisodeListItemEpoxyModel
import com.example.rickandmortywiki.epoxy.uimodel.LongHeaderEpoxyModel
import com.example.rickandmortywiki.model.EpisodeUiModel
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
                LongHeaderEpoxyModel(header).id("header_${header}")
            }
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (showShimmering) {
            LongHeaderEpoxyModel(header = null).id("header_shimmering").addTo(this)
            repeat(6) {
                EpisodeListItemEpoxyModel(episode = null, null).id(it).addTo(this)
            }
            return
        }
        super.addModels(models)
    }
}