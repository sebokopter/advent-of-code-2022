plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.6")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}

benchmark {
    // Create configurations
    configurations {
        named("main") { // main configuration is created automatically, but you can change its defaults
            iterationTime = 3 // time in seconds per iteration
            iterationTimeUnit = "sec"
        }
    }

    // Setup targets
    targets {
        // This one matches compilation base name, e.g. 'jvm', 'jvmTest', etc
        register("main")
    }
}