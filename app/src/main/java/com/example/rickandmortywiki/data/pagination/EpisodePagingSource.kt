package com.example.rickandmortywiki.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortywiki.model.DataTransformUtils.transformEpisodeResponseToEpisode
import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.model.networkresponse.EpisodeByIdPagingSource
import com.example.rickandmortywiki.repository.SharedRepository

class EpisodePagingSource(
    private val apiRepository: SharedRepository
): PagingSource<Int, Episode>() {
    // this function will invoke every time it needs to load a new page
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1

        val response = apiRepository.getEpisodeByPageId(pageNumber)

        return LoadResult.Page(
            data = response?.results?.map {
                transformEpisodeResponseToEpisode(it)
            } ?: emptyList(),
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(response?.info?.next)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
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
        return next?.split("?page=")?.get(1)?.toInt()
    }
}