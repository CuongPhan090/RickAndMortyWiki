package com.example.rickandmortywiki.data.pagination

import androidx.paging.DataSource
import com.example.rickandmortywiki.data.model.GetCharacterByIdResponse
import com.example.rickandmortywiki.repository.SharedRepository
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: SharedRepository
): DataSource.Factory<Int, GetCharacterByIdResponse>() {

    // create paging data source
    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharacterDataSource(
            coroutineScope,
            repository
        )
    }
}
