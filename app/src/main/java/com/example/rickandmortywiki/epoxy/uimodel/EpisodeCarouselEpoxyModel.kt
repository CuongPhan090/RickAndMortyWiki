package com.example.rickandmortywiki.epoxy.uimodel

import android.view.View
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.ModelEpisodeCarouselItemsBinding
import com.example.rickandmortywiki.epoxy.ViewBindingKotlinModel
import com.example.rickandmortywiki.model.domain.Episode

data class EpisodeCarouselEpoxyModel(
    val episode: Episode?,
    val onClick: (Int?) -> Unit
) : ViewBindingKotlinModel<ModelEpisodeCarouselItemsBinding>(R.layout.model_episode_carousel_items) {
    override fun ModelEpisodeCarouselItemsBinding.bind() {
        episode?.let { episodeDetails ->
            showShimmerEpisodeCarousel(false, this)
            episodeName.text = episodeDetails.name
            episodeAirDay.text = episodeDetails.airDate
            episodeSeason.text = episodeDetails.getFormattedSeasonTruncated()
            root.setOnClickListener {
                onClick(episodeDetails.id)
            }
        } ?: run {
            showShimmerEpisodeCarousel(true, this)
        }
    }

    private fun showShimmerEpisodeCarousel(
        show: Boolean,
        binding: ModelEpisodeCarouselItemsBinding
    ) {
        binding.apply {
            if (show) {
                modelEpisodeCarouselItemsShimmer.startShimmer()
                modelEpisodeCarouselItemsShimmer.visibility = View.VISIBLE
                episodeName.visibility = View.GONE
                episodeAirDay.visibility = View.GONE
                episodeSeason.visibility = View.GONE
                root.strokeColor =
                    root.resources.getColor(R.color.shimmer_view_background, root.context.theme)
            } else {
                modelEpisodeCarouselItemsShimmer.stopShimmer()
                modelEpisodeCarouselItemsShimmer.visibility = View.GONE
                episodeName.visibility = View.VISIBLE
                episodeAirDay.visibility = View.VISIBLE
                episodeSeason.visibility = View.VISIBLE
                root.strokeColor =
                    root.resources.getColor(R.color.black, root.context.theme)
            }
        }
    }
}
