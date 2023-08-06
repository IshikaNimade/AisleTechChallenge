package com.ishmit.aisleassignment.ui.fragments.phoneNumberScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ishmit.aisleassignment.databinding.FragmentPhoneNumberBinding
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.utils.gone
import com.ishmit.aisleassignment.utils.visible
import com.ishmit.aisleassignment.utils.ktx.ValidateStrings.isValidPhoneNumber
import com.ishmit.aisleassignment.utils.showToast
import org.koin.android.ext.android.inject

class PhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentPhoneNumberBinding
    private val phoneViewModel by inject<PhoneViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding
        binding = FragmentPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up UI elements and listeners
        setupViewsAndListeners()

        // Observe phone number login response
        observePhoneNumberResponse()
    }

    // Set up UI elements and listeners
    private fun setupViewsAndListeners() {
        // Set up button click listener for phone number login
        binding.continueButton.setOnClickListener {
            // Validate and initiate phone number login process
            validateAndLoginPhoneNumber()
        }
    }

    // Verify OTP with the provided country code and mobile number
    private fun validateAndLoginPhoneNumber() {
        val countryCode = binding.countryCode.text.toString()
        val mobileNumber = binding.number.text.toString()
        val number = countryCode + mobileNumber

        // Validate phone number format using custom extension function
        if (isValidPhoneNumber(countryCode, mobileNumber)) {
            // Call phone number login function from ViewModel
            phoneViewModel.phoneNumberLogin(number)
        } else {
            // Show a toast message for invalid phone number format
            showToast("Please check your number and try again")
        }
    }

    // Observe phone number verification response from ViewModel
    private fun observePhoneNumberResponse() {
        phoneViewModel.phoneNumberResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResponseRequest.Loading -> {
                    // Show loading state by displaying progress bar and hiding button
                    showLoadingState()
                }
                is ResponseRequest.Success -> {
                    // Handle successful phone number login response
                    handleSuccess(response.data.status)
                }
                is ResponseRequest.Failure -> {
                    // Handle phone number login failure and show error message
                    response.error?.let { handleFailure(it) }
                }
                else -> {}
            }
        }
    }

    // Show Loading State
    private fun showLoadingState() {
        binding.progressBar.visible()
        binding.continueButton.gone()
    }

    // Handle OTP verification success
    private fun handleSuccess(status: Boolean) {
        // Hide progress bar and show button after response
        binding.progressBar.gone()
        binding.continueButton.visible()

        if (status) {
            // Navigate to OTP verification screen with country code and mobile number
            navigateToOtpFragment()
        } else {
            // Show a toast message for unsuccessful phone number status
            showToast("Phone number status false. Please try with a different number.")
        }
    }

    // Handle OTP verification Failure
    private fun handleFailure(error: String) {
        // Hide progress bar and show error message toast
        binding.progressBar.gone()
        showToast(error)
    }

    // Navigate to OtpFragment
    private fun navigateToOtpFragment() {
        // Navigate to OTP verification screen with provided arguments
        val action = PhoneNumberFragmentDirections.actionPhoneFragmentToOtpFragment(
            countryCode = binding.countryCode.text.toString(),
            mobileNumber = binding.number.text.toString()
        )
        findNavController().navigate(action)
    }
}
