package com.example.data.resource

sealed class ResponseResult<out T> {

    data object Loading : ResponseResult<Nothing>()

    data class LocalData<T>(val data: T) : ResponseResult<T>()

    data class Success<T>(val data: T) : ResponseResult<T>()

    data class Error(val error: ResponseError) : ResponseResult<Nothing>()

}