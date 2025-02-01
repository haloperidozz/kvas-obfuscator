package com.github.haloperidozz.obfuscator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "kvas-obfuscator",
    ) {
        App()
    }
}