package com.task.routing

import com.task.models.UserSerializable
import com.task.storage.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.userRouting() {
    routing {
        swaggerUI("api")
        route("/user") {
            post {
                val newUser: UserSerializable =
                    kotlin.runCatching { call.receiveNullable<UserSerializable>() }.getOrNull()
                        ?: return@post call.respond(HttpStatusCode.MethodNotAllowed, "Invalid input")
                var id: Int? = null
                try {
                    transaction {
                        id = User.new {
                            name = newUser.name
                            surname = newUser.surname
                            patronymic = newUser.patronymic
                            phoneNumber = newUser.phoneNumber
                            email = newUser.email
                        }.id.value

                    }
                } catch (ex: ExposedSQLException) {
                    if (ex.message?.contains("phone_number") == true || ex.message?.contains("email") == true) {
                        return@post call.respond(
                            HttpStatusCode.MethodNotAllowed, "Email or phone number already used"
                        )
                    }
                }
                if (id != null) {
                    call.respond("User added with id=$id")
                } else {
                    call.respond(HttpStatusCode.MethodNotAllowed, "New user add error")
                }
            }
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                    HttpStatusCode.BadRequest, "Invalid id supplied"
                )
                val user = transaction {
                    User.findById(id)
                }
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                } else {
                    call.respond(UserSerializable(user))
                }
            }
        }
    }
}
