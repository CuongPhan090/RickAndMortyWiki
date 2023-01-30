package com.example.rickandmortywiki.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmortywiki.data.pagination.CharacterSearchPagingSource
import com.example.rickandmortywiki.data.pagination.CharactersPagingSource
import com.example.rickandmortywiki.data.pagination.EpisodePagingSource
import com.example.rickandmortywiki.model.EpisodeUiModel
import com.example.rickandmortywiki.model.domain.Character
import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.repository.SharedRepository
import com.example.rickandmortywiki.util.Event
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = PAGE_SIZE * 2 // page size x 2 or 3

class SharedViewModel(private val apiRepository: SharedRepository) : ViewModel() {

    val allEpisodePagination = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(apiRepository)
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { model: EpisodeUiModel?, model2: EpisodeUiModel? ->
            // Initial separator for the first season header (before the whole list)
            if (model == null) {
                return@insertSeparators EpisodeUiModel.Header("Season 1")
            }

            // Footer
            if (model2 == null) {
                return@insertSeparators null
            }

            // Make sure we only care the items (episodes)
            if (model is EpisodeUiModel.Header || model2 is EpisodeUiModel.Header) {
                return@insertSeparators null
            }

            // Determine if a separator is needed
            val episode1 = (model as EpisodeUiModel.Item).episode
            val episode2 = (model2 as EpisodeUiModel.Item).episode
            return@insertSeparators if (episode2.season != episode1.season) {
                EpisodeUiModel.Header("Season ${episode2.season}")
            } else {
                null
            }
        }
    }


    private val _localExceptionEvent: MutableStateFlow<Event<CharacterSearchPagingSource.LocalException>> = MutableStateFlow(Event(CharacterSearchPagingSource.LocalException.EmptySearch))
    val localExceptionEvent: StateFlow<Event<CharacterSearchPagingSource.LocalException>>
        get() = _localExceptionEvent.asStateFlow()

    private var currentUserSearch: String? = null
    private var pagingSource: CharacterSearchPagingSource? = null
        get() {
            if (field == null || field?.invalid == true) {
                field = CharacterSearchPagingSource(apiRepository, currentUserSearch) { localEvent ->
                    _localExceptionEvent.value = Event(localEvent)
                }
            }
            return field
        }

    val searchCharacterPagination = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)


    val charactersPagination: Flow<PagingData<CharacterByIdResponse>> = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        CharactersPagingSource(apiRepository)
    }.flow.cachedIn(viewModelScope)


    private val _episode: MutableStateFlow<Episode?> = MutableStateFlow(Episode())
    val episode: StateFlow<Episode?>
        get() = _episode.asStateFlow()

    fun fetchEpisode(episodeId: String) = viewModelScope.launch {
        _episode.value = apiRepository.getEpisode(episodeId)
    }

    private val _characterDetail: MutableStateFlow<Character?> =
        MutableStateFlow(Character())
    val characterDetail: StateFlow<Character?>
        get() = _characterDetail.asStateFlow()

    fun getCharacter(characterId: String) = viewModelScope.launch {
        _characterDetail.value = apiRepository.getCharacter(characterId)
    }

    fun submitQuery(keyword: String) {
        currentUserSearch = keyword
        pagingSource?.invalidate()
    }
}
