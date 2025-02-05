package com.github.haloperidozz.obfuscator.data.favorite

import kotlinx.browser.window
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FavoriteDataSourceImpl : FavoriteDataSource {
    private val favoritesFlow = MutableStateFlow(
        window.localStorage.getItem(ITEM_NAME)?.split("|")?.toSet() ?: emptySet()
    )

    override fun all(): Flow<Set<String>> {
        return favoritesFlow
    }

    override suspend fun save(favorites: Set<String>) {
        favoritesFlow.emit(favorites)
        window.localStorage.setItem(ITEM_NAME, favorites.joinToString("|"))
    }

    companion object {
        private const val ITEM_NAME = "favorites"
    }
}