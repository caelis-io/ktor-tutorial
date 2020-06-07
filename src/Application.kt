package me.caelis

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import me.caelis.entities.login.LoginRequest
import me.caelis.entities.login.LoginResponse

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val client = HttpClient(Apache) {


        routing {

            get("/") {
                call.respondText("Hello Ktor!")
            }

            get("/test") {
                call.respondText("This is a test")
            }

            post("/login") {
                val loginRequest = call.receive<LoginRequest>()

                if (loginRequest.username == "admin" && loginRequest.password == "adminpw") {
                    call.respond(LoginResponse(true, "Login successful!"))
                } else {
                    call.respond(LoginResponse(false, "Invalid password!"))
                }
            }
        }

    }

}

