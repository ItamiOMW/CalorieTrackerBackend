package com.itami.routes.user

import com.itami.API_VERSION
import com.itami.data.dto.request.AddWeightRequest
import com.itami.data.dto.request.EditWeightRequest
import com.itami.data.dto.request.UpdateUserRequest
import com.itami.plugins.JWT_AUTH
import com.itami.service.user.UserService
import com.itami.utils.AppException
import com.itami.utils.convert
import com.itami.utils.userId
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

private const val USER = "$API_VERSION/user"
private const val USER_WEIGHTS = "$USER/weights"
private const val USER_WEIGHT_ID = "$USER/weights/{weightId}"

fun Route.user(
    userService: UserService,
) {
    authenticate(JWT_AUTH) {
        put(USER) {
            val userId = context.userId()
            val multipart = call.receiveMultipart()

            var updateUserRequest: UpdateUserRequest? = null
            var profilePictureName: String? = null
            var profilePictureByteArray: ByteArray? = null

            multipart.forEachPart { partData ->
                when (partData) {
                    is PartData.FormItem -> {
                        if (partData.name == "update_user_request") {
                            updateUserRequest = Json.decodeFromString(partData.value)
                        }
                    }

                    is PartData.FileItem -> {
                        if (partData.name == "profile_picture") {
                            val (fileName, fileBytes) = partData.convert()
                            profilePictureName = fileName
                            profilePictureByteArray = fileBytes
                        }
                    }

                    else -> Unit
                }
            }

            updateUserRequest?.let { request ->
                val userResponse = userService.updateUser(
                    userId = userId,
                    updateUserRequest = request,
                    profilePictureName = profilePictureName,
                    profilePictureByteArray = profilePictureByteArray
                )
                call.respond(status = HttpStatusCode.OK, message = userResponse)
            } ?: throw AppException.BadRequestException()
        }
        delete(USER) {
            val userId = context.userId()
            userService.deleteUser(userId)
            call.respond(status = HttpStatusCode.OK, message = "Account has been deleted.")
        }
        get(USER_WEIGHTS) {
            val userId = context.userId()
            val weights = userService.getWeights(userId = userId)
            call.respond(status = HttpStatusCode.OK, message = weights)
        }
        post(USER_WEIGHTS) {
            val userId = context.userId()
            val addWeightRequest = call.receive<AddWeightRequest>()
            val weight = userService.addWeight(userId = userId, addWeightRequest = addWeightRequest)
            call.respond(status = HttpStatusCode.Created, message = weight)
        }
        put(USER_WEIGHT_ID) {
            val userId = context.userId()
            val weightId = call.parameters["weightId"]?.toIntOrNull() ?: throw AppException.BadRequestException()
            val editWeightRequest = call.receive<EditWeightRequest>()
            val weight = userService.editWeight(
                userId = userId,
                weightId = weightId,
                editWeightRequest = editWeightRequest
            )
            call.respond(status = HttpStatusCode.OK, message = weight)
        }
    }
}