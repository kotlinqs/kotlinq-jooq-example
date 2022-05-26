import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.github.kotlinqs.kotlinq:com.github.kotlinqs.gradle.plugin:0.1-SNAPSHOT")
    }
}

apply(plugin="com.github.kotlinqs")

plugins {
    kotlin("jvm") version "1.6.21"
    id("nu.studer.jooq") version "7.1.1"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.kotlinqs:kotlinq-jooq:0.1-SNAPSHOT")
    implementation("org.jooq:jooq:3.16.6")
    implementation("com.h2database:h2:2.1.212")
    jooqGenerator("org.jooq:jooq-meta-extensions:3.16.6")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    url = "jdbc:h2:./example"
                }
                generator.apply {
                }
            }
        }
    }
}

kotlinq {
    debug = true
}