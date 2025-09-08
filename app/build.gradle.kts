import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    /**********************************
     * Supplied by New Project template
     **********************************/

    // This needs to be "id" due to buildSrc using Android Gradle plugin
    id("com.android.application")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    /**********************************
     * Added by Codepunk
     **********************************/

    // Kotlin Symbol Processing (KSP)
    alias(libs.plugins.ksp)

    // Hilt
    alias(libs.plugins.hilt)

    // Serialization
    alias(libs.plugins.serialization)
}

android {
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
        debug {
            buildConfigField(
                "long",
                "DATA_REFRESH_DURATION_MINUTES",
                "1L"
            )
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val minutesPerDay = 60L * 24L
            buildConfigField(
                "long",
                "DATA_REFRESH_DURATION",
                "${minutesPerDay * 30}L"
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
        extractLocalProperty(
            project.rootProject,
            "TheMovieDbAccessToken",
            "THE_MOVIE_DB_ACCESS_TOKEN"
        )

        makeConstStringValue(
            name = "APPLICATION_NAME",
            value = "MoviePunk"
        )

        buildConfigField(
            "long",
            "OK_HTTP_CLIENT_CACHE_SIZE",
            "10 * 1024 * 1024"
        )

        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://api.themoviedb.org/3/\""
        )
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        optIn.add("kotlin.time.ExperimentalTime")
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

    // Arrow (and Quiver)
    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)
    implementation(libs.arrow.core.retrofit)
    implementation(libs.quiver)

    // Datastore
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.proto)

    // Datetime
    implementation(libs.kotlinx.datetime)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Lifecycle
    implementation(libs.lifecycle.process)

    // Navigation
    implementation(libs.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    testImplementation(libs.room.testing)
    implementation(libs.room.paging)

    // Serialization
    implementation(libs.serialization)

    // Timber
    implementation(libs.timber)

}
