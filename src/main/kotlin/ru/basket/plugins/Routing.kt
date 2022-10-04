package ru.basket.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class Test (
    val text : String
)


fun Application.configureRouting() {

    routing {
        get("/test") {
            call.respond(
                listOf(
                    Test(text = "Test message"),
                    Test(text = "Echo test message"))
                )
        }
    }
}
