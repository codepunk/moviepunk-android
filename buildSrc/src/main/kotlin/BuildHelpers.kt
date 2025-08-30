@file:Suppress("DEPRECATION", "unused")

import com.android.build.gradle.api.ApplicationVariant
import java.util.Properties
import org.gradle.api.Project

const val BUILD_CONFIG_STRING = "String"
const val RES_VALUE_STRING = "string"

/**
 * Version 1 of keys/intents/etc. logic:
 * https://github.com/codepunk/codepunk-legacy-android-app-core-codepunk/blob/master/app/build.gradle
 *
 * Version 2 of keys/intents/etc. logic:
 * https://github.com/codepunk/codepunk-android-app-core-2021/blob/main/app/helpers.gradle
 */

fun ApplicationVariant.extractLocalProperty(
    project: Project,
    propertyName: String,
    name: String,
    defaultValue: String = "[$propertyName is missing from local.properties]",
    type: String = BUILD_CONFIG_STRING,
    formatValue: (String) -> String = { "\"$it\"" }
) {
    val properties = Properties()
    properties.load(project.file("local.properties").inputStream())
    val propertyValue = properties.getProperty(propertyName) ?: defaultValue
    buildConfigField(type, name, formatValue(propertyValue))
}

/**
 * Writes a string value to both BuildConfig and the app's resources.
 * @param name The name of the value.
 * @param value The string value to write.
 */
fun ApplicationVariant.makeConstStringValue(
    name: String,
    value: String
) {
    buildConfigField(BUILD_CONFIG_STRING, name.uppercase(), "\"$value\"")
    resValue(RES_VALUE_STRING, name.lowercase(), value)
}

fun ApplicationVariant.makeKey(suffix: String) {
    buildConfigField(
        BUILD_CONFIG_STRING,
        "KEY_${suffix.uppercase()}",
        "\"$applicationId.${suffix.uppercase()}\""
    )
}

enum class IntentEntityType(val prefix: String, val value: String) {
    ACTION("ACTION_", "action"),
    CATEGORY("CATEGORY_", "category"),
    EXTRA("EXTRA_", "extra")
}

fun ApplicationVariant.makeIntentEntity(
    type: IntentEntityType,
    suffix: String,
) {
    val value = "$applicationId.intent.${type.value}.${suffix.lowercase()}"
    buildConfigField(
        BUILD_CONFIG_STRING,
        "${type.prefix}${suffix.uppercase()}",
        "\"$value\""
    )
    resValue(
        RES_VALUE_STRING,
        "intent_${type.value}_${suffix.lowercase()}",
        value
    )
}
