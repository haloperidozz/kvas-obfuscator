package com.github.haloperidozz.obfuscator.util

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.Flow

enum class ExternalEvent {
    Back
}

enum class PlatformType {
    Desktop,
    Android,
    Web
}

expect class Platform {
    val type: PlatformType

    val externalEvents: Flow<ExternalEvent>

    fun saveToFile(text: String, defaultFileName: String = "output.txt")

    fun share(text: String)

    @Composable
    fun dynamicColorScheme(darkTheme: Boolean): ColorScheme?
}

@Composable
fun LocalPlatformProvider(platform: Platform, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalPlatform provides platform) {
        content()
    }
}

val LocalPlatform = staticCompositionLocalOf<Platform> {
    error("no provided")
}