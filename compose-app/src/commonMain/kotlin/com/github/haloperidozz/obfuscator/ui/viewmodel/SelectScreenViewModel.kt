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
package com.github.haloperidozz.obfuscator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.haloperidozz.obfuscator.settings.SettingsStorage
import com.github.haloperidozz.obfuscator.generator.TextGenerators
import com.github.haloperidozz.obfuscator.ui.model.SelectScreenUiState
import com.github.haloperidozz.obfuscator.ui.viewmodel.shared.SharedTextGeneratorHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectScreenViewModel(
    private val settingsStorage: SettingsStorage,
    private val sharedTextGeneratorHolder: SharedTextGeneratorHolder
) : ViewModel() {
    private val initialState: SelectScreenUiState by lazy {
        val sharedUiState = sharedTextGeneratorHolder.uiState.value

        SelectScreenUiState(
            generators = TextGenerators.entries,
            favoriteGenerators = emptyList(),
            currentGenerator = sharedUiState.currentGenerator,
        )
    }

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<SelectScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsStorage.getItem(FAVORITE_SETTINGS_KEY).collect { favorite ->
                val favoriteIds = favorite?.split("|")?.toSet() ?: emptySet()

                _uiState.update { currentState ->
                    val generators = currentState.generators
                    currentState.copy(
                        favoriteGenerators = generators.filter { it.name in favoriteIds }
                    )
                }
            }
        }

        viewModelScope.launch {
            sharedTextGeneratorHolder.uiState.collect { sharedState ->
                _uiState.update { currentState ->
                    currentState.copy(
                        currentGenerator = sharedState.currentGenerator,
                    )
                }
            }
        }
    }

    fun toggleFavorite(generator: TextGenerators) {
        val updatedFavorites = _uiState.value.favoriteGenerators.toMutableList()

        updatedFavorites.apply {
            if (contains(generator)) remove(generator) else add(generator)
        }

        _uiState.update { currentState ->
            currentState.copy(favoriteGenerators = updatedFavorites)
        }

        viewModelScope.launch {
            settingsStorage.setItem(
                key = FAVORITE_SETTINGS_KEY,
                value = updatedFavorites.joinToString("|") { it.name }
            )
        }

    }

    fun selectGenerator(generator: TextGenerators) {
        sharedTextGeneratorHolder.selectGenerator(generator)
    }

    companion object {
        private const val FAVORITE_SETTINGS_KEY = "favorite"
    }
}
