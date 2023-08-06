package com.ishmit.aisleassignment.ui.fragments.OtpScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ishmit.aisleassignment.databinding.FragmentOtpBinding
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.utils.gone
import com.ishmit.aisleassignment.utils.showToast
import com.ishmit.aisleassignment.utils.visible
import org.koin.android.ext.android.inject

class OtpFragment : Fragment() {

    // Inject the ViewModel using Koin
    private val otpViewModel by inject<OtpViewModel>()

    private lateinit var binding: FragmentOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Extract arguments from navigation
        val args = OtpFragmentArgs.fromBundle(requireArguments())
        val countryCode = args.countryCode
        val mobileNumber = args.mobileNumber

        // Set up UI elements and listeners
        setupUIAndListeners(countryCode, mobileNumber)

        // Observe OTP verification response
        observeOtpVerificationResponse()
    }

    // Set up UI elements and listeners
    private fun setupUIAndListeners(countryCode: String, mobileNumber: String) {
        // Set country code and mobile number in the UI
        binding.countryCode.text = countryCode
        binding.number.text = mobileNumber

        // Set up button click listeners
        binding.edit.setOnClickListener {
            navigateToPhoneNumberFragment()
        }
        binding.continueButton.setOnClickListener {
            verifyOtp(countryCode, mobileNumber)
        }
    }

    // Navigate to PhoneNumberFragment
    private fun navigateToPhoneNumberFragment() {
        val action = OtpFragmentDirections.actionOtpFragmentToPhoneNumberFragment()
        findNavController().navigate(action)
    }

    // Verify OTP with the provided country code and mobile number
    private fun verifyOtp(countryCode: String, mobileNumber: String) {
        val number = countryCode + mobileNumber
        val otp = binding.otp.text.toString()
        otpViewModel.verifyOtp(number, otp)
    }

    // Observe OTP verification response from ViewModel
    private fun observeOtpVerificationResponse() {
        otpViewModel.otpResponse.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ResponseRequest.Loading -> {
                    binding.progressBar.visible()
                }
                is ResponseRequest.Success -> {
                    binding.progressBar.gone()
                    handleOtpVerificationSuccess(uiState.data.token)
                }
                is ResponseRequest.Failure -> {
                    handleOtpVerificationFailure()
                }
            }
        }
    }

    // Handle OTP verification success
    private fun handleOtpVerificationSuccess(token: String) {
        val action = OtpFragmentDirections.actionOtpFragmentToNotesFragment(
            token = token
        )
        findNavController().navigate(action)
    }

    // Handle OTP verification failure
    private fun handleOtpVerificationFailure() {
        showToast("Please enter correct OTP")
    }
}
