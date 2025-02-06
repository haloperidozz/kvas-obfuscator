package com.github.haloperidozz.obfuscator.data.favorite

import com.github.haloperidozz.obfuscator.data.settings.SettingsStorage
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo
import com.github.haloperidozz.obfuscator.repository.FavoriteTextGeneratorRepository
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import kotlinx.coroutines.flow.*

class FavoriteTextGeneratorRepositoryImpl(
    private val settings: SettingsStorage,
    private val textGeneratorRepository: TextGeneratorRepository
) : FavoriteTextGeneratorRepository {
    override suspend fun all(): Flow<List<TextGeneratorInfo<*>>> {
        return settings.getItem(FAVORITE_KEY).map { item ->
            val favoriteSet = item?.split("|")?.toSet() ?: emptySet()
            favoriteSet.mapNotNull { favorite -> textGeneratorRepository[favorite] }
        }
    }

    override suspend fun save(favorites: List<TextGeneratorInfo<*>>) {
        settings.setItem(FAVORITE_KEY, favorites.joinToString("|") { it.id })
    }

    companion object {
        private const val FAVORITE_KEY = "favorite"
    }
}