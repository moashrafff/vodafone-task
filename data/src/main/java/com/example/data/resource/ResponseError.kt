package com.example.data.resource

enum class ResponseErrorType {
    UNKNOWN,
    HTTP,
    NO_CONNECTIVITY
}

data class ResponseError(
    val errorType: ResponseErrorType,
    val errorMessage: String
)