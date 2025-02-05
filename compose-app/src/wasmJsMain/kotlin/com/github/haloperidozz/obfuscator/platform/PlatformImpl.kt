package com.github.haloperidozz.obfuscator.platform

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

class PlatformImpl : Platform {
    override val type: PlatformType = PlatformType.Web

    @Composable
    override fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        return null
    }

    override fun saveToFile(text: String, defaultFileName: String) {
        // No-op: The save to file functionality is not supported on web
    }

    override fun share(text: String) {
        // No-op: Share functionality is not supported on web
    }
}