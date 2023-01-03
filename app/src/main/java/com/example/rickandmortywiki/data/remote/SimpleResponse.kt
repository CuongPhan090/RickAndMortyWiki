package com.example.rickandmortywiki.data.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import retrofit2.Response

data class SimpleResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {

    companion object {
        fun <T> success(data: Response<T>): SimpleResponse<T>  {
            return SimpleResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }

    sealed class Status {
        object Success : Status()
        object Failure: Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = !failed && this.data?.isSuccessful == true

    val body: T?
        get() = data?.body()
}

data class ApolloSimpleResponse<T : Operation.Data>(
    val status: Status,
    val data: ApolloResponse<T>?,
    val exception: Exception?
) {

    companion object {
        fun <T : Operation.Data> success(data: ApolloResponse<T>): ApolloSimpleResponse<T> {
            return ApolloSimpleResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T : Operation.Data> failure(exception: Exception): ApolloSimpleResponse<T> {
            return ApolloSimpleResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }

    sealed class Status {
        object Success : Status()
        object Failure: Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = !failed && this.data?.hasErrors() != true

    val body: T?
        get() = data?.data
}
