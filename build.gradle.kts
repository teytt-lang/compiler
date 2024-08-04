import com.strumenta.antlrkotlin.gradle.AntlrKotlinTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.strumenta.antlr-kotlin") version "1.0.0-RC4"
}

group = "diogommarto.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val generateKotlinGrammarSource = tasks.register<AntlrKotlinTask>("generateKotlinGrammarSource") {
    dependsOn("cleanGenerateKotlinGrammarSource")

    // ANTLR .g4 files are under {example-project}/antlr
    // Only include *.g4 files. This allows tools (e.g., IDE plugins)
    // to generate temporary files inside the base path
    source = fileTree(layout.projectDirectory) {
        include("**/*.g4")
    }

    println(source.files)

    // We want the generated source files to have this package name
    val pkgName = "diogommarto.kotlin.generated"
    packageName = pkgName

    // We want visitors alongside listeners.
    // The Kotlin target language is implicit, as is the file encoding (UTF-8)
    arguments = listOf("-visitor","-no-listener","-package",pkgName)

    // Generated files are outputted inside build/generatedAntlr/{package-name}
    val outDir = "generatedAntlr/${pkgName.replace(".", "/")}"
    outputDirectory = layout.buildDirectory.dir(outDir).get().asFile
}

kotlin{
    sourceSets{
        dependencies {
            implementation("com.strumenta:antlr-kotlin-runtime:1.0.0-RC4")
        }
    }
}

tasks.withType<KotlinCompile> {
    dependsOn(generateKotlinGrammarSource)
}

kotlin {
    sourceSets {
        val main by getting {
            kotlin.srcDir(layout.buildDirectory.dir("generatedAntlr"))
        }
    }
}

