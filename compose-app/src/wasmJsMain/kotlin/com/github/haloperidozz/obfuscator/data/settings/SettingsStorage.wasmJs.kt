package com.github.haloperidozz.obfuscator.data.settings

import kotlinx.browser.window
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class SettingsStorage {
    actual fun getItem(key: String): Flow<String?> {
        return flowOf(window.localStorage.getItem(key))
    }

    actual suspend fun setItem(key: String, value: String) {
        window.localStorage.setItem(key, value)
    }

    actual suspend fun removeItem(key: String) {
        window.localStorage.removeItem(key)
    }

}