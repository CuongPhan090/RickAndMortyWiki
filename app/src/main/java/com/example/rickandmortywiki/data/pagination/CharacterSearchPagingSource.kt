package com.example.rickandmortywiki.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortywiki.model.DataTransformUtils
import com.example.rickandmortywiki.model.domain.Characters
import com.example.rickandmortywiki.repository.SharedRepository

class CharacterSearchPagingSource(
    private val apiRepository: SharedRepository,
    private val keyword: String?,
    private val localException: (LocalException) -> Unit
) : PagingSource<Int, Characters>() {

    sealed class LocalException(
        val title: String? = null,
        val description: String? = null
    ) : Exception() {
        object EmptySearch : LocalException(
            title = "Start typing to search!"
        )

        object NoResult : LocalException(
            title = "Whoops!",
            description = "Looks like your search returned no results"
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        if (keyword.isNullOrEmpty()) {
            val exception = LocalException.EmptySearch
            localException(exception)
            return LoadResult.Error(exception)
        }

        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1
        val data = apiRepository.searchCharacter(keyword, pageNumber)
        if (data == null) {
            val exception = LocalException.NoResult
            localException(exception)
            return LoadResult.Error(exception)
        }

        return LoadResult.Page(
            data = data.results?.map {
                DataTransformUtils.transformCharacterResponseToCharacter(it)
            } ?: emptyList(),
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(data?.info?.next),
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
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

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("page=")?.get(1)?.split('&')?.get(0)?.toInt()
    }
}