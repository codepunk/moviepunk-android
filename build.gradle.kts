// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    /**********************************
     * Supplied by New Project template
     **********************************/

    // This needs to be "id" instead of "alias" due to buildSrc using Android Gradle plugin
    // alias(libs.plugins.android.application) apply false
    id("com.android.application") apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    /**********************************
     * Added by Codepunk
     **********************************/

    // Kotlin Symbol Processing (KSP)
    alias(libs.plugins.ksp) apply false

    // Hilt
    alias(libs.plugins.hilt) apply false

    // Kotlin Serialization
    alias(libs.plugins.serialization) apply false

}
