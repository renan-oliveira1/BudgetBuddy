// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val room_version = "2.6.1"
    id("androidx.room") version "$room_version" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}