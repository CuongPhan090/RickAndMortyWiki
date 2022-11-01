package com.example.rickandmortywiki.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortywiki.model.Character
import com.example.rickandmortywiki.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel: ViewModel() {
    private val apiRepository = ApiRepository()

    private val _characterDetail: MutableStateFlow<Character> = MutableStateFlow(Character())
    val characterDetail: StateFlow<Character>
        get() = _characterDetail.asStateFlow()


    init {
        viewModelScope.launch {
            _characterDetail.value = apiRepository.getCharacterById(1)
        }
    }
}
