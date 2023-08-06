package com.ishmit.aisleassignment.utils.ktx

object ValidateStrings {

    // Function to validate a phone number with country code
    fun isValidPhoneNumber(countryCode: String, mobileNumber: String): Boolean {
        return isValidCountryCode(countryCode) && isValidMobileNumber(mobileNumber)
    }

    // Function to validate the country code
    private fun isValidCountryCode(countryCode: String): Boolean {
        val countryCodeRegex = "^\\+[1-9]\\d{0,2}\$".toRegex()
        return countryCode.matches(countryCodeRegex)
    }

    // Function to validate the mobile number
    private fun isValidMobileNumber(mobileNumber: String): Boolean {
        val mobileNumberRegex = "^[1-9]\\d{9}\$".toRegex()
        val isValidMobileNumber = mobileNumber.matches(mobileNumberRegex)
        val isValidMobileNumberLength = mobileNumber.length == 10
        return isValidMobileNumber && isValidMobileNumberLength
    }
}
