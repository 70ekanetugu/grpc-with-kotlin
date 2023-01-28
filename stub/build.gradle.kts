import com.google.protobuf.gradle.id

plugins {
    id("com.google.protobuf") version "0.9.2"
    kotlin("jvm") version "1.8.0"
}

repositories {
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    api("io.grpc:grpc-protobuf:1.52.1")
    api("io.grpc:grpc-stub:1.52.1")
    api("io.grpc:grpc-kotlin-stub:1.3.0")
    api("com.google.protobuf:protobuf-java-util:3.21.12")
    api("com.google.protobuf:protobuf-kotlin:3.21.12")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.12"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.52.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}