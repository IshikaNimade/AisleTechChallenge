package com.ishmit.aisleassignment.data.remote.repository

import com.ishmit.aisleassignment.data.remote.network.ApiService
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.data.models.*
import com.ishmit.aisleassignment.utils.apiCall.ApiCall

class UserRepository(private val apiService: ApiService) {

    /**
     * Function to perform phone number login.
     *
     * @param number The phone number to be logged in.
     * @return [ResponseRequest] containing the login response.
     */
    suspend fun phoneNumberLogin(number: String): ResponseRequest<PhoneNumberResponse> =
        ApiCall.apiCall {
            apiService.phoneNumberLogin(PhoneNumberRequest(number))
        }

    /**
     * Function to verify OTP (One-Time Password) for a given phone number.
     *
     * @param number The phone number for which OTP is to be verified.
     * @param otp The OTP entered by the user.
     * @return [ResponseRequest] containing the OTP verification response.
     */
    suspend fun verifyOtp(number: String, otp: String): ResponseRequest<OtpResponse> =
        ApiCall.apiCall {
            apiService.verifyOtp(OtpRequest(number, otp))
        }

    /**
     * Function to fetch notes (profiles) data using the provided authentication token.
     *
     * @param authToken The authentication token to access the notes data.
     * @return [ResponseRequest] containing the notes data response.
     */
    suspend fun getNotes(authToken: String): ResponseRequest<NotesResponse> =
        ApiCall.apiCall {
            apiService.getNotes(authToken)
        }
}
