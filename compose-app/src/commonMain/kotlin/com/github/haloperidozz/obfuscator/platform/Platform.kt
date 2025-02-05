package com.github.haloperidozz.obfuscator.platform

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

interface Platform {
    val type: PlatformType

    @Composable
    fun dynamicColorScheme(darkTheme: Boolean): ColorScheme?

    fun saveToFile(text: String, defaultFileName: String = "output.txt")

    fun share(text: String)
}