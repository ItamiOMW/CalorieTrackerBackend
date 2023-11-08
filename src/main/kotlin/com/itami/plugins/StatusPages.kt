package com.itami.plugins

import com.itami.data.response.ErrorResponse
import com.itami.utils.AppException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<AppException> { call, cause ->
            cause.printStackTrace()
            call.respond(
                status = cause.httpStatusCode,
                message = ErrorResponse(cause.message),
            )
        }
        exception<Exception> { call, cause ->
            cause.printStackTrace()
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse("Unknown error occurred.")
            )
        }
    }
}

