package com.example.rickandmortywiki.data.remote

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.rickandmortywiki.RickAndMortyWikiApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object  NetworkLayer {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(getLoggingHttpClient())
        .build()

    private val rickAndMortyService: RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)
    }

    val apiClient = ApiClient(rickAndMortyService)

    private fun getLoggingHttpClient(): OkHttpClient {
        val okHttpInterceptor: Interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val chuckInterceptor = ChuckerInterceptor.Builder(RickAndMortyWikiApplication.context)
            .collector(ChuckerCollector(RickAndMortyWikiApplication.context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(okHttpInterceptor)
            .addInterceptor(chuckInterceptor)
            .build()
    }
}
