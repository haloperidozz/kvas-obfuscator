package com.github.haloperidozz.obfuscator.util

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.core.app.ShareCompat
import kotlinx.coroutines.flow.Flow

actual class Platform(
    private val context: Context,
    actual val externalEvents: Flow<ExternalEvent>
) {
    actual val type: PlatformType = PlatformType.Android

    actual fun saveToFile(text: String, defaultFileName: String) {
        // TODO?
    }

    actual fun share(text: String) {
        ShareCompat.IntentBuilder(context).apply {
            setText(text)
            setType("text/plain")
            startChooser()
        }
    }

    @Composable
    actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        return null
    }
}