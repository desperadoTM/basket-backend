package ru.basket.features.registation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.basket.cache.InMemoryCache
import ru.basket.cache.TokenCache
import ru.basket.features.LoginModel
import ru.basket.features.LoginResponse
import ru.basket.utils.isValidEmail
import java.util.*

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}