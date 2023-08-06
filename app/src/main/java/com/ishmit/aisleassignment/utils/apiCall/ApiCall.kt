package com.ishmit.aisleassignment.utils.apiCall

import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import retrofit2.Response

object ApiCall {
    // Function to perform API call and handle response states
    suspend fun <T : Any> apiCall(apiCall: suspend () -> Response<T>): ResponseRequest<T> {
        return try {
            // Execute the provided API call
            val response = apiCall()

            // Handle the response based on different cases
            handleResponse(response)
        } catch (e: Exception) {
            // Return Failure state with the exception message
            ResponseRequest.Failure(e.message)
        }
    }

    // Handle the response and return the appropriate state
    private fun <T : Any> handleResponse(response: Response<T>): ResponseRequest<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                // Return Success state with the response body
                ResponseRequest.Success(body)
            } else {
                // Return Failure state with an error message
                ResponseRequest.Failure("Response body is null.")
            }
        } else {
            // Return Failure state with the error response body
            ResponseRequest.Failure(response.errorBody()?.string())
        }
    }
}
