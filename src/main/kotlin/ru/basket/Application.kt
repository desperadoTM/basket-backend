package ru.basket

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import ru.basket.features.login.configureLoginRouting
import ru.basket.features.registation.configureRegisterRouting
import ru.basket.plugins.*

fun main() {

    Database.connect("")


    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureLoginRouting()
        configureRegisterRouting()
    }.start(wait = true)
}

private fun Database.Companion.connect(s: String, driver: String, manager: String, password: String) {

}

