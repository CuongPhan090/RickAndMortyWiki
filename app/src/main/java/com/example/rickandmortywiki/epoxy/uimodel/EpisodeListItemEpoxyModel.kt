package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelEpisodeListItemBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Episode

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
        binding.apply {
            if (show) {
                modelEpisodeListItemShimmering.startShimmer()
                modelEpisodeListItemShimmering.visibility = View.VISIBLE
            } else {
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
