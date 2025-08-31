plugins {
    `kotlin-dsl`
}

dependencies {
    // Android Gradle Plugin
    implementation(libs.android.gradle.plugin)

    // Kotlin Gradle Plugin
    // implementation(libs.kotlin.gradle.plugin)

    // JavaPoet - Required because of an artifact introduced when using Hilt with buildSrc
    implementation(libs.javapoet)
}
