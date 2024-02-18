plugins {
    id("java-library")
    id("idea")
}

val release =  System.getProperty("release")?.toBoolean() ?: false
val versionSuffix = if (release) "" else "-SNAPSHOT"

version = "0.1${versionSuffix}"
group = "org.viktori"

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
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}
