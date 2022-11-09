package com.example.rickandmortywiki.data.pagination

import androidx.paging.DataSource
import com.example.rickandmortywiki.model.CharacterByIdResponse
import com.example.rickandmortywiki.repository.SharedRepository
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: SharedRepository
): DataSource.Factory<Int, CharacterByIdResponse>() {

    // create paging data source
    override fun create(): DataSource<Int, CharacterByIdResponse> {
        return CharacterDataSource(
            coroutineScope,
            repository
        )
    }
}
