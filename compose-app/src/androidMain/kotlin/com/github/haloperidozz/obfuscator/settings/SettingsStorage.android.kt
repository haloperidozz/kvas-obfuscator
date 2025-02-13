/*
 * Copyright (C) 2025 haloperidozz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.haloperidozz.obfuscator.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

actual class SettingsStorage(context: Context) {
    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            context.filesDir.resolve(DATASTORE_FILENAME).absolutePath.toPath()
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
    }
}
