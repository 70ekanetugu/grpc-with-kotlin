plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("maven-publish")
}


version = "0.0.1-SNAPSHOT"

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

publishing {
    repositories {
        maven {
            name = "github-package-registry"
            url = uri("https://maven.pkg.github.com/70ekanetugu/grpc-with-kotlin")
            credentials {
                username = project.findProperty("GITHUB_USER") as? String ?: ""
                password = project.findProperty("GITHUB_TOKEN") as? String ?: ""
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}