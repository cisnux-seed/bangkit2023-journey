package com.dicoding.newsapp.data

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Nothing has no instances. You can use Nothing to represent "a value that never exists": for example,
     * if a function has the return type of Nothing, it means that it never returns (always throws an exception).
     */
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
