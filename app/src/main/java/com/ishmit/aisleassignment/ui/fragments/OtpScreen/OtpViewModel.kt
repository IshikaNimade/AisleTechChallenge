package com.ishmit.aisleassignment.ui.fragments.OtpScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ishmit.aisleassignment.data.models.OtpResponse
import com.ishmit.aisleassignment.data.remote.repository.UserRepository
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

// ViewModel class for OTP verification screen
class OtpViewModel (private val repository: UserRepository) : ViewModel() {

    // LiveData to hold OTP verification response
    private val _otpResponse = MutableLiveData<ResponseRequest<OtpResponse>>()
    val otpResponse: LiveData<ResponseRequest<OtpResponse>> = _otpResponse

    // Function to verify OTP
    fun verifyOtp(number: String, otp: String) {
        viewModelScope.launch {
            // Notify UI that OTP verification is in progress
            _otpResponse.value = ResponseRequest.Loading

            // Call the repository's verifyOtp function
            val response = repository.verifyOtp(number, otp)

            // Update the LiveData with the OTP verification response
            _otpResponse.value = response
        }
    }

}
