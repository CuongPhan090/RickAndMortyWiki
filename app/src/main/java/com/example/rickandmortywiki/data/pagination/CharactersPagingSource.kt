package com.example.rickandmortywiki.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortywiki.model.networkresponse.CharacterByIdResponse
import com.example.rickandmortywiki.repository.SharedRepository

class CharactersPagingSource(
    private val sharedRepository: SharedRepository
): PagingSource<Int, CharacterByIdResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterByIdResponse> {
        val pageNumber = params.key ?: 1

        val response = sharedRepository.getCharacters(pageNumber)
        return LoadResult.Page(
            response?.results ?: emptyList(),
            response?.info?.prev,
            response?.info?.next
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterByIdResponse>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}