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
