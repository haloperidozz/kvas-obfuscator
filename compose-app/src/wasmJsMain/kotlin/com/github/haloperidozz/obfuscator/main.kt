package com.github.haloperidozz.obfuscator

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.github.haloperidozz.obfuscator.di.commonModules
import kotlinx.browser.document
import org.koin.compose.KoinApplication

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        KoinApplication(
            application = { commonModules() }
        ) {
            App()
        }
    }
}