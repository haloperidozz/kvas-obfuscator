package com.github.haloperidozz.obfuscator.data.favorite

import kotlinx.coroutines.flow.Flow

interface FavoriteDataSource {
    fun all(): Flow<Set<String>>

    suspend fun save(favorites: Set<String>)
}