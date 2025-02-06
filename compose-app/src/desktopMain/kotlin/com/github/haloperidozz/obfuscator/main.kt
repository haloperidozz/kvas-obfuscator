package com.github.haloperidozz.obfuscator

import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.haloperidozz.obfuscator.di.commonModules
import com.github.haloperidozz.obfuscator.util.ExternalEvent
import com.github.haloperidozz.obfuscator.util.LocalPlatformProvider
import com.github.haloperidozz.obfuscator.util.Platform
import org.koin.compose.KoinApplication

fun main() = application {
    val platform = remember { Platform() }

    Window(
        onCloseRequest = ::exitApplication,
        title = "kvas-obfuscator",
        onKeyEvent = {
            if (it.type == KeyEventType.KeyUp && it.key == Key.Escape) {
                platform.produceEvent(ExternalEvent.Back)
            }
            false
        },
    ) {
        LocalPlatformProvider(platform) {
            KoinApplication(application = { commonModules() }) {
                App()
            }
        }
    }
}