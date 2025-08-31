import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    /**********************************
     * Supplied by New Project template
     **********************************/

    // This needs to be "id" due to buildSrc using Android Gradle plugin
    id(libs.plugins.android.application.get().pluginId)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    /**********************************
     * Added by Codepunk
     **********************************/

    // Kotlin Symbol Processing (KSP)
    alias(libs.plugins.ksp)

    // Hilt
    alias(libs.plugins.hilt)
}

android {
    libs.plugins.android.application
    namespace = "com.codepunk.moviepunk"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.codepunk.moviepunk"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    applicationVariants.all {
        // This is probably temporary
        makeConstStringValue(
            name = "app_name",
            value = "MoviePunk"
        )
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    /**********************************
     * Supplied by New Project template
     **********************************/

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /**********************************
     * Added by Codepunk
     **********************************/

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

}
