package com.itami.utils

import io.ktor.http.*

sealed class AppException(
    override val message: String,
    val httpStatusCode: HttpStatusCode,
): Exception() {

    data class UnauthorizedException(override val message: String = "Unauthorized.") : AppException(
        message = message,
        httpStatusCode = HttpStatusCode.Unauthorized
    )

    data class ForbiddenException(override val message: String = "Forbidden.") : AppException(
        message = message,
        httpStatusCode = HttpStatusCode.Forbidden
    )

    data class BadRequestException(override val message: String = "Bad Request.") : AppException(
        message = message,
        httpStatusCode = HttpStatusCode.BadRequest
    )

    data class NotFoundException(override val message: String = "Not found.") : AppException(
        message = message,
        httpStatusCode = HttpStatusCode.NotFound
    )

    data class ConflictException(override val message: String = "Conflict.") : AppException(
        message = message,
        httpStatusCode = HttpStatusCode.Conflict
    )

}