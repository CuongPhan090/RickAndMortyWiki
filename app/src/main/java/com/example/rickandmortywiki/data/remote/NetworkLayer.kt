package com.example.rickandmortywiki.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.rickandmortywiki.data.remote.apiclient.ApolloApiClient
import okhttp3.OkHttpClient

object NetworkLayer {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .okHttpClient(OkHttpClient())
        .build()

    val apolloApiClient: ApolloApiClient by lazy {
        ApolloApiClient(RickAndMortyApolloService(), apolloClient)
    }

//    private fun getLoggingHttpClient(): OkHttpClient {
//        val okHttpInterceptor: Interceptor = HttpLoggingInterceptor()
//            .setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        val chuckInterceptor = ChuckerInterceptor.Builder(RickAndMortyWikiApplication.context)
//            .collector(ChuckerCollector(RickAndMortyWikiApplication.context))
//            .maxContentLength(250000L)
//            .redactHeaders(emptySet())
//            .alwaysReadResponseBody(false)
//            .build()
//
//        return OkHttpClient.Builder()
//            .addInterceptor(okHttpInterceptor)
//            .addInterceptor(chuckInterceptor)
//            .build()
//    }
}
