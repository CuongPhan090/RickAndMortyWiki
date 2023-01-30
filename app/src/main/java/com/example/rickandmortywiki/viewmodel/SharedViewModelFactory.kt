package com.example.rickandmortywiki.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortywiki.repository.SharedRepository

class SharedViewModelFactory(private val sharedRepository: SharedRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) return SharedViewModel(sharedRepository) as T
        else throw IllegalArgumentException()
    }
}

