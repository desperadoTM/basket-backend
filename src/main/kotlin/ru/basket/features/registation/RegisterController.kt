package ru.basket.features.registation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.basket.database.tokens.TokenDTO
import ru.basket.database.tokens.Tokens
import ru.basket.database.users.UserDTO
import ru.basket.database.users.Users
import ru.basket.utils.isValidEmail
import java.util.*

class RegisterController(private val call : ApplicationCall) {

    suspend fun registerNewUser() {

        val registerReceive = call.receive<RegisterReceive>()

        if (!registerReceive.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDTO = Users.fetchUser(registerReceive.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = registerReceive.login,
                        password = registerReceive.password,
                        email = registerReceive.email,
                        username = ""
                    )
                )
            } catch (e : ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }

            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(),
                    login = registerReceive.login,
                    token = token
                )
            )
            call.respond(RegisterResponse(token))
        }

    }

}
