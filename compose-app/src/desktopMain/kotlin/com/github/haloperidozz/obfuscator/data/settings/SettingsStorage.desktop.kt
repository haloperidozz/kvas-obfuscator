package com.github.haloperidozz.obfuscator.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

actual class SettingsStorage {
    private val appDataPath: String = when {
        System.getenv("APPDATA") != null -> System.getenv("APPDATA")
        System.getenv("XDG_CONFIG_HOME") != null -> System.getenv("XDG_CONFIG_HOME")
        else -> "${System.getProperty("user.home")}/.config"
    }

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            ("$appDataPath/$FOLDER_NAME/$DATASTORE_FILENAME").toPath()
        }
    )

    actual fun getItem(key: String): Flow<String?> {
        return dataStore.data.map { preferences -> preferences[stringPreferencesKey(key)] }
    }

    actual suspend fun setItem(key: String, value: String) {
        dataStore.edit { preferences -> preferences[stringPreferencesKey(key)] = value }
    }

    actual suspend fun removeItem(key: String) {
        dataStore.edit { preferences -> preferences.remove(stringPreferencesKey(key)) }
    }

    companion object {
        private const val DATASTORE_FILENAME = "kvas_obfuscator.preferences_pb"
        private const val FOLDER_NAME = "KvasObfuscator"
    }
}