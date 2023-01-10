package com.example.rickandmortywiki.epoxy.controller

import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortywiki.epoxy.uimodel.CharacterCarouselEpoxyModel
import com.example.rickandmortywiki.model.domain.Character

class EpisodeDetailsEpoxyController(
    private val listOfCharacters: List<Character>?,
    private val characterOnClick: (Int?) -> Any
) : EpoxyController() {
    override fun buildModels() {
        listOfCharacters?.forEach {
            CharacterCarouselEpoxyModel(it.id, it.image, it.name, characterOnClick).id("${it.image}_${it.name}").addTo(this)
        }
    }
}
