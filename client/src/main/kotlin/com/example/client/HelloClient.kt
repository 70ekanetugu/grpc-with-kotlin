package com.example.client

import com.example.hello.GreeterGrpcKt
import com.example.hello.helloRequest
import io.grpc.Grpc
import io.grpc.ManagedChannel
import io.grpc.TlsChannelCredentials
import java.util.concurrent.TimeUnit

class HelloClient(private val channel: ManagedChannel): AutoCloseable {
    private val stub: GreeterGrpcKt.GreeterCoroutineStub = GreeterGrpcKt.GreeterCoroutineStub(channel)

    suspend fun sayHello(name: String) {
        val request = helloRequest { this.name = name }
        val response = stub.sayHello(request)
        println(response.message)
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

suspend fun main() {
    val channel = Grpc.newChannelBuilder(
        "127.0.0.1:8443",
        TlsChannelCredentials.newBuilder()
            .trustManager(HelloClient::javaClass.javaClass.getResourceAsStream("/ca/localCA.crt"))
            .build()
    ).build()
//    val channel = ManagedChannelBuilder
//        .forAddress("127.0.0.1", 8443)
//        .useTransportSecurity()
//        .build()
    val client = HelloClient(channel)
    client.sayHello("kanetugu")
}