package com.github.haloperidozz.obfuscator.util

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

actual class Platform {
    actual val type: PlatformType = PlatformType.Web
    actual val externalEvents: Flow<ExternalEvent> = emptyFlow()

    actual fun saveToFile(text: String, defaultFileName: String) {
        // TODO?
    }

    actual fun share(text: String) {
        // No-op: Share functionality is not supported on web
    }

    @Composable
    actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        return null
    }
}