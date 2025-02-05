package com.github.haloperidozz.obfuscator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.haloperidozz.obfuscator.di.commonModules
import org.koin.compose.KoinApplication

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kvas-obfuscator",
    ) {
        KoinApplication(
            application = { commonModules() }
        ) {
            App()
        }
    }
}