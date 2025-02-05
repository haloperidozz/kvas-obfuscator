package com.github.haloperidozz.obfuscator.data.favorite

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

class FavoriteDataSourceImpl(private val context: Context) : FavoriteDataSource {
    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            context.filesDir.resolve(DATASTORE_FILENAME).absolutePath.toPath()
        }
    )

    override fun all(): Flow<Set<String>> {
        return dataStore.data.map {
            preferences -> preferences[FAVORITES_KEY] ?: emptySet()
        }
    }

    override suspend fun save(favorites: Set<String>) {
        dataStore.edit { preferences -> preferences[FAVORITES_KEY] = favorites }
    }

    companion object {
        private const val DATASTORE_FILENAME = "kvas_obfuscator.preferences_pb"
        private val FAVORITES_KEY = stringSetPreferencesKey("favorites")
    }
}