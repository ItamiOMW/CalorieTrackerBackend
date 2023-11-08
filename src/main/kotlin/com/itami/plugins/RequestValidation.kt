package com.itami.plugins

import com.itami.utils.AppException
import com.itami.data.request.GoogleLoginRequest
import com.itami.data.request.GoogleRegisterRequest
import com.itami.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*


fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<GoogleRegisterRequest> { request ->
            if (request.age > Constants.MAX_AGE || request.age < Constants.MIN_AGE) {
                throw AppException.BadRequestException("Please specify your real age")
            }
            if (request.heightCm > Constants.MAX_HEIGHT_CM || request.heightCm < Constants.MIN_HEIGHT_CM) {
                throw AppException.BadRequestException("Please specify your real height")
            }
            if (request.weightGrams > Constants.MAX_WEIGHT_GRAMS || request.weightGrams < Constants.MIN_WEIGHT_GRAMS) {
                throw AppException.BadRequestException("Please specify your real weight")
            }
            if (request.googleIdToken.isBlank()) {
                throw AppException.BadRequestException("Google id token is blank")
            }
            ValidationResult.Valid
        }
        validate<GoogleLoginRequest> { request ->
            if (request.googleIdToken.isBlank()) {
                throw AppException.BadRequestException("Google id token is blank")
            }
            ValidationResult.Valid
        }
    }
}