package com.zekri.mediaplayercompose.common

sealed class Result<out T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : Result<T>(data = data)
    class Error<T>(error: String?) : Result<T>(error = error)
    class Loading<T> : Result<T>()
}