// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
}
true // Needed to make the Suppress annotation work for the plugins block

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.classPath.hilt)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}