package com.example.rickandmortywiki.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.rickandmortywiki.*
import com.example.rickandmortywiki.type.FilterCharacter


class RickAndMortyApolloService {
    suspend fun getCharacters(pageIndex: Int, apolloClient: ApolloClient): ApolloResponse<CharactersQuery.Data> =
        apolloClient.query(CharactersQuery(pageIndex)).execute()

    suspend fun getCharacter(id: String, apolloClient: ApolloClient): ApolloResponse<CharacterQuery.Data> =
        apolloClient.query(CharacterQuery(id)).execute()

    suspend fun getEpisodes(pageIndex: Int, apolloClient: ApolloClient): ApolloResponse<EpisodesQuery.Data> =
        apolloClient.query(EpisodesQuery(pageIndex)).execute()

    suspend fun getEpisode(id: String, apolloClient: ApolloClient): ApolloResponse<EpisodeDetailsQuery.Data> =
        apolloClient.query(EpisodeDetailsQuery(id)).execute()

    suspend fun searchCharacter(pageIndex: Int, filter: FilterCharacter, apolloClient: ApolloClient): ApolloResponse<CharacterSearchQuery.Data> =
        apolloClient.query(CharacterSearchQuery(pageIndex, filter)).execute()
}
