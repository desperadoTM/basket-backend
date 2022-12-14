package ru.basket.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.basket.database.tokens.TokenDTO
import ru.basket.database.tokens.Tokens
import ru.basket.database.users.Users
import ru.basket.features.LoginModel
import ru.basket.features.LoginResponse
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginModel>()

        val userDTO = Users.fetchUser(receive.login)
        println("receive -> $receive, dto -> $userDTO")

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        rowId = UUID.randomUUID().toString(),
                        login = receive.login,
                        token = token
                    )
                )
                call.respond(LoginResponse(token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }

}