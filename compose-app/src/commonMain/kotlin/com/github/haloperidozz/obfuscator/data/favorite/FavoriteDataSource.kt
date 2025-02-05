package com.github.haloperidozz.obfuscator.data.favorite

import kotlinx.coroutines.flow.Flow

interface FavoriteDataSource {
    suspend fun all(): Flow<List<String>>

    suspend fun save(favorites: List<String>)
}