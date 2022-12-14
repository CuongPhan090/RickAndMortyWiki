package com.example.rickandmortywiki.data.pagination

import androidx.paging.PageKeyedDataSource
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.repository.SharedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharacterDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: SharedRepository
) : PageKeyedDataSource<Int, CharacterByIdResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getListOfCharacters(1)

            if (page == null) {
                callback.onResult(
                    emptyList(),
                    null,
                    null
                )
                return@launch
            }

            page.results?.let {
                callback.onResult(
                    it,
                    null,
                    getPageIndexFromNext(page.info?.next)
                )
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getListOfCharacters(params.key)

            if (page == null) {
                callback.onResult(
                    emptyList(),
                    null
                )
                return@launch
            }

            page.results?.let {
                callback.onResult(
                    it,
                    params.key + 1
                )
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharacterByIdResponse>
    ) {
        // Nothing to do
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}
