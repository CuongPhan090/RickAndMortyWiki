package com.example.rickandmortywiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.data.pagination.CharactersDataSourceFactory
import com.example.rickandmortywiki.model.domain.Character
import com.example.rickandmortywiki.repository.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = PAGE_SIZE * 2 // page size x 2 or 3

class SharedViewModel : ViewModel() {
    private val apiRepository = SharedRepository()


    private val _characterDetail: MutableStateFlow<Character?> =
        MutableStateFlow(Character())
    val characterDetail: StateFlow<Character?>
        get() = _characterDetail.asStateFlow()


    fun refreshCharacter(characterId: Int) {
        viewModelScope.launch {
            _characterDetail.value = apiRepository.getCharacterById(characterId)
        }
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
