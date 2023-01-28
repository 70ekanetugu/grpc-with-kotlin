plugins {
    kotlin("jvm") version "1.8.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":stub"))
    runtimeOnly("io.grpc:grpc-netty-shaded:1.52.1")
}

application {
    mainClass.set("com.example.client.HelloClientKt")
}