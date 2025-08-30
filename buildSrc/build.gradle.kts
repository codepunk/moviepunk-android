plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle.plugin)
//    implementation(libs.kotlin.gradle.plugin)
//    implementation(libs.javapoet) // Required because of an artifact introduced when using Hilt
}
