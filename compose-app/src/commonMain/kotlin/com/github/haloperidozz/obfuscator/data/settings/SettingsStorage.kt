package com.github.haloperidozz.obfuscator.data.settings

import kotlinx.coroutines.flow.Flow

expect class SettingsStorage {
    fun getItem(key: String): Flow<String?>

    suspend fun setItem(key: String, value: String)

    suspend fun removeItem(key: String)
}