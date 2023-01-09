package com.example.rickandmortywiki.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortywiki.model.EpisodeUiModel
import com.example.rickandmortywiki.repository.SharedRepository

class EpisodePagingSource(
    private val apiRepository: SharedRepository
): PagingSource<Int, EpisodeUiModel>() {
    // this function will invoke every time it needs to load a new page
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeUiModel> {
        val response = apiRepository.getEpisodes(params.key ?: 1)

        val x =  response?.episode?.map {
            EpisodeUiModel.Item(
                episode = it
            )
        }

        return LoadResult.Page(
            data = x ?: emptyList(),
            prevKey = response?.info?.prev,
            nextKey = response?.info?.next
        )
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeUiModel>): Int? {
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
