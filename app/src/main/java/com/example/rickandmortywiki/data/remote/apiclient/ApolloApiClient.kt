package com.example.rickandmortywiki.data.remote.apiclient

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.example.rickandmortywiki.data.remote.ApolloSimpleResponse
import com.example.rickandmortywiki.data.remote.RickAndMortyApolloService
import com.example.rickandmortywiki.type.FilterCharacter

class ApolloApiClient(
    private val rickAndMortyApolloService: RickAndMortyApolloService,
    private val apolloClient: ApolloClient
) {
    suspend fun getCharacters(pageIndex: Int) =
        apolloSafeApiCall { rickAndMortyApolloService.getCharacters(pageIndex, apolloClient) }

    suspend fun getCharacter(id: String) =
        apolloSafeApiCall { rickAndMortyApolloService.getCharacter(id, apolloClient) }

    suspend fun getEpisodes(pageIndex: Int) =
        apolloSafeApiCall { rickAndMortyApolloService.getEpisodes(pageIndex, apolloClient) }

    suspend fun getEpisode(id: String) =
        apolloSafeApiCall { rickAndMortyApolloService.getEpisode(id, apolloClient) }

    suspend fun searchCharacters(pageIndex: Int, filter: FilterCharacter) =
        apolloSafeApiCall { rickAndMortyApolloService.searchCharacter(pageIndex, filter, apolloClient)}

    private inline fun <T : Operation.Data> apolloSafeApiCall(apiCall: () -> ApolloResponse<T>): ApolloSimpleResponse<T> {
        return try {
            ApolloSimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            ApolloSimpleResponse.failure(e)
        }
    }
}