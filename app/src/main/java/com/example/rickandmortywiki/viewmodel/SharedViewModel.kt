package com.example.rickandmortywiki.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.data.model.GetCharacterByIdResponse
import com.example.rickandmortywiki.repository.SharedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {
    private val apiRepository = SharedRepository()

    private val _characterDetail: MutableStateFlow<GetCharacterByIdResponse?> = MutableStateFlow(GetCharacterByIdResponse())
    val characterDetail: StateFlow<GetCharacterByIdResponse?>
        get() = _characterDetail.asStateFlow()


    fun refreshCharacter(characterId: Int) {
        viewModelScope.launch {
            _characterDetail.value = apiRepository.getCharacterById(characterId)
        }
    }
}
