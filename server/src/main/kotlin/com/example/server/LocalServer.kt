package com.example.server

import com.example.hello.*
import io.grpc.Server
import io.grpc.ServerBuilder
class LocalServer(private val port: Int = 50051) {
    private val server: Server = ServerBuilder
        .forPort(port)
        .useTransportSecurity(
            javaClass.getResourceAsStream("/server.crt"),
            javaClass.getResourceAsStream("/server.pem")
        )
        .addService(HelloService())
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("Shutdown server.")
                this@LocalServer.stop()
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    internal class HelloService: GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest) = helloReply {
            message = "Hello ${request.name} Reply!"
        }
    }
}

fun main() {
    val server = LocalServer(8443)
    server.start()
    server.blockUntilShutdown()
}