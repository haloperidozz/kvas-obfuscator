package com.github.haloperidozz.obfuscator.data.favorite

import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo
import com.github.haloperidozz.obfuscator.repository.FavoriteRepository
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import kotlinx.coroutines.flow.*

class FavoriteRepositoryImpl(
    private val dataSource: FavoriteDataSource,
    private val textGeneratorRepository: TextGeneratorRepository
) : FavoriteRepository {
    override suspend fun all(): Flow<List<TextGeneratorInfo<*>>> {
        return dataSource.all().map { favoriteList ->
            favoriteList.mapNotNull { favorite -> textGeneratorRepository[favorite] }
        }
    }

    override suspend fun save(favorites: List<TextGeneratorInfo<*>>) {
        dataSource.save(favorites.map { generator -> generator.id }.toSet())
    }
}