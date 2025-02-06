package com.github.haloperidozz.obfuscator

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.github.haloperidozz.obfuscator.di.commonModules
import com.github.haloperidozz.obfuscator.util.LocalPlatformProvider
import com.github.haloperidozz.obfuscator.util.Platform
import kotlinx.browser.document
import org.koin.compose.KoinApplication

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        LocalPlatformProvider(remember { Platform() }) {
            KoinApplication(application = { commonModules() }) {
                App()
            }
        }
    }
}