package com.example.rickandmortywiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.data.pagination.CharactersDataSourceFactory
import com.example.rickandmortywiki.data.pagination.EpisodePagingSource
import com.example.rickandmortywiki.model.domain.Characters
import com.example.rickandmortywiki.repository.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = PAGE_SIZE * 2 // page size x 2 or 3

class SharedViewModel : ViewModel() {
    private val apiRepository = SharedRepository()

    val flow = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(apiRepository)
    }.flow.cachedIn(viewModelScope)

    private val _charactersDetail: MutableStateFlow<Characters?> =
        MutableStateFlow(Characters())
    val charactersDetail: StateFlow<Characters?>
        get() = _charactersDetail.asStateFlow()


    fun refreshCharacter(characterId: Int) = viewModelScope.launch {
        _charactersDetail.value = apiRepository.getCharacterById(characterId)
    }

    fun getAllEpisode(pageNumber: Int): Any = viewModelScope.launch {
        apiRepository.getEpisodeByPageId(pageNumber)
    }

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .build()

    private val dataSourceFactory = CharactersDataSourceFactory(
        coroutineScope = viewModelScope,
        repository = apiRepository
    )

    val listOfCharacters: LiveData<PagedList<CharacterByIdResponse>> = LivePagedListBuilder(
        dataSourceFactory,
        pageListConfig
    ).build()
}
