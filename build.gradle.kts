// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // This needs to be "id" instead of "alias" due to buildSrc using Android Gradle plugin
    // alias(libs.plugins.android.application) apply false
    id(libs.plugins.android.application.get().pluginId) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // This is just to mark the plugin in libs.version.toml as "used"
    libs.plugins.android.application
}
