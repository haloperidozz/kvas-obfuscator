package com.github.haloperidozz.obfuscator.repository

import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo
import kotlinx.coroutines.flow.Flow

interface FavoriteTextGeneratorRepository {
    suspend fun all(): Flow<List<TextGeneratorInfo<*>>>

    suspend fun save(favorites: List<TextGeneratorInfo<*>>)
}