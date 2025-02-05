package com.github.haloperidozz.obfuscator.platform

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.core.app.ShareCompat

class PlatformImpl(private val context: Context) : Platform {
    override val type: PlatformType = PlatformType.Android

    @Composable
    override fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        return null
    }

    override fun saveToFile(text: String, defaultFileName: String) {
        // TODO?
    }

    override fun share(text: String) {
        ShareCompat.IntentBuilder(context).setText(text).setType("text/plain").startChooser()
    }
}