plugins {
    java
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.allopen") version "1.4.21"
    kotlin("plugin.noarg") version "1.4.21"
    id("me.champeau.gradle.jmh") version "0.5.2"
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

noArg {
    annotation("com.mapk.common.NoArg")
}

group = "com.mapk"
version = "1.0"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    // commons
    implementation(kotlin("stdlib"))
    compileOnly(group = "org.jetbrains", name = "annotations", version = "20.1.0")
    // for warning suppression
    jmhImplementation("org.slf4j:slf4j-simple:1.7.25")

    // targets of benchmark
    implementation("com.github.ProjectMapK:FastKFunction:0.1.3")

    // for Mapper benchmark
    implementation("com.github.ProjectMapK:KMapper:0.33")
    // mapstruct
    implementation("org.mapstruct:mapstruct:1.4.1.Final")
    jmhAnnotationProcessor("org.mapstruct:mapstruct-processor:1.4.1.Final")
    // orika
    jmhImplementation("ma.glasnost.orika:orika-core:1.5.4")
    // modelmapper
    jmhImplementation("org.modelmapper:modelmapper:2.3.9")

    // for RowMapper benchmark
    implementation("com.github.ProjectMapK:KRowMapper:0.17")
    implementation(group = "org.springframework", name = "spring-jdbc", version = "5.3.2")
    implementation(group = "com.h2database", name = "h2", version = "1.4.200")

    // for test
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.7.0") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileJmhKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    // https://qiita.com/wrongwrong/items/16fa10a7f78a31830ed8
    jmhJar {
        exclude("META-INF/versions/9/module-info.class")
    }
}

jmh {
    warmupForks = 2
    warmupBatchSize = 3
    warmupIterations = 3
    warmup = "1s"

    fork = 2
    batchSize = 3
    iterations = 2
    timeOnIteration = "1500ms"

    failOnError = true
    isIncludeTests = false

    resultFormat = "CSV"
}
