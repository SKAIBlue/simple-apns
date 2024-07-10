import org.jreleaser.model.Active
import org.kordamp.gradle.plugin.base.plugins.Sonar
import java.net.URI

plugins {
    kotlin("jvm") version "1.9.23"
    id("java-library")
    id("maven-publish")
    id("org.jreleaser") version "1.12.0"
}

group = "io.github.skaiblue"
version = "0.0.1"

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "io.github.skaiblue"
            artifactId = "simple-apns"

            from(components["java"])

            pom {
                name = "simple-apns"
                description = "Simple APNs Library for Java/Kotlin"
                url = "https://github.com/SKAIBlue/simple-apns"
                inceptionYear = "2024"
                licenses {
                    license {
                        name = "MIT"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }
                developers {
                    developer {
                        id = "skaiblue"
                        name = "Jong Woo Park"
                        email = "jwmtp2@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/SKAIBlue/simple-apns.git"
                    developerConnection = "scm:git:ssh://github.com/SKAIBlue/simple-apns.git"
                    url = "https://github.com/SKAIBlue/simple-apns"
                }
            }
        }
    }

    repositories {
        maven {
            url = URI(layout.buildDirectory.dir("staging-deploy").get().asFile.absolutePath)
        }
    }
}

//jreleaser {
//    signing {
//        active = Active.ALWAYS
//        armored = true
//    }
//    deploy {
//        maven {
//            mavenCentral {
//
//            }
//        }
//    }
//}


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("io.jsonwebtoken:jjwt:0.12.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}