package com.example.data.resource

import android.util.Log

import retrofit2.HttpException
import java.io.IOException

suspend fun <DTO, ENTITY> apiCall(
    operation: suspend () -> DTO,
    converter: (DTO) -> ENTITY,
    isValidResponse: (DTO) -> Boolean
): ResponseResult<ENTITY> {
    return try {
        val result = operation()
        if (!isValidResponse(result)) throw Exception()
        ResponseResult.Success(converter(result))
    } catch (e: IOException) {
        Log.e("apiCall", "IOException -> ${e.message}")
        ResponseResult.Error(
            error = ResponseError(
                errorType = ResponseErrorType.NO_CONNECTIVITY,
                errorMessage = e.localizedMessage ?: "No Connectivity"
            )
        )
    } catch (e: HttpException) {
        Log.e("apiCall", "HttpException -> ${e.message}")
        ResponseResult.Error(
            error = ResponseError(
                errorType = ResponseErrorType.HTTP,
                errorMessage = errorCodeMessages[e.response()?.code()] ?: "Something went wrong"
            )
        )
    } catch (e: Exception) {
        Log.e("apiCall", "Exception -> ${e.message}")
        ResponseResult.Error(
            error = ResponseError(
                errorType = ResponseErrorType.UNKNOWN,
                errorMessage = e.message ?: "Something went wrong"
            )
        )
    }
}


private val errorCodeMessages = mapOf(
    400 to "Bad Request",
    401 to "Unauthorized",
    403 to "Forbidden",
    404 to "Not Found",
    405 to "Method Not Allowed",
    409 to "Conflict",
    500 to "Internal Server Error",
    502 to "Bad Gateway",
    503 to "Service Unavailable",
    504 to "Gateway Timeout"
)