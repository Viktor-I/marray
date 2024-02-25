plugins {
    id("java-library")
    id("maven-publish")
    id("idea")
}

val release =  System.getProperty("release")?.toBoolean() ?: false
val versionSuffix = if (release) "" else "-SNAPSHOT"

version = "0.1${versionSuffix}"
group = "org.viktori"
description = "A light-weight library for working with safe arrays and matrices, built upon the Java collections framework."

repositories {
    mavenCentral()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.10.2")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }

    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifacts {
                artifact(tasks.jar)
                artifact(tasks.getByName("sourcesJar"))
                artifact(tasks.getByName("javadocJar"))
            }

            pom {
                name = project.base.archivesName
                description = project.description
                url = "https://github.com/Viktor-I/matteray/"

                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }

                developers {
                    developer {
                        id = "viktori"
                        name = "Viktor Ingemansson"
                    }
                }

                scm {
                    url = "https://github.com/Viktor-I/matteray/"
                }

                issueManagement {
                    url = "https://github.com/Viktor-I/matteray/issues"
                    system = "GitHub issues"
                }
            }
        }
    }
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}

tasks.javadoc {
    (options as? StandardJavadocDocletOptions)?.apply {
        tags = listOf("apiNote", "implSpec", "implNote")
        addBooleanOption("html5", true)
    }
}