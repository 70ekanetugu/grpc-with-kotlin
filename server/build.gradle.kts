plugins {
    kotlin("jvm") version "1.8.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":stub"))
    runtimeOnly("io.grpc:grpc-netty:1.52.1")
}

application {
    mainClass.set("com.example.server.LocalServerKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.example.server.LocalServerKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    )
}