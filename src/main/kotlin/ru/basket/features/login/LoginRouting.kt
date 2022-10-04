package ru.basket.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.basket.cache.InMemoryCache
import ru.basket.cache.TokenCache
import ru.basket.features.LoginModel
import ru.basket.features.LoginResponse
import ru.basket.features.registation.RegisterReceive
import ru.basket.features.registation.RegisterResponse
import ru.basket.plugins.Test
import java.util.UUID


fun Application.configureLoginRouting() {

    routing {
        post("/login") {
            val receive = call.receive<LoginModel>()
            val first = InMemoryCache.userList.firstOrNull { it.login == receive.login }

            if (first == null) {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            } else {
                if (first.password == receive.password) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(receive.login, token))
                    call.respond(LoginResponse(token))
                } else {
                   call.respond(HttpStatusCode.BadRequest, "Invalid password")
                }
            }
        }
    }
}