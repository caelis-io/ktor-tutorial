package me.caelis

import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.caelis.entity.LoginRequest
import me.caelis.entity.LoginResponse

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val client = HttpClient(Apache) {

        install(ContentNegotiation) {
            gson {

            }

        }

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
                    call.respond(LoginResponse(false, "Credentials are invalid!"))
                }

            }

        }

    }

}

