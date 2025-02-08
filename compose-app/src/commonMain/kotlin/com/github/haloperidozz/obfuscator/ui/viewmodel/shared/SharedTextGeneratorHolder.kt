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
package com.github.haloperidozz.obfuscator.ui.viewmodel.shared

import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorValue
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import com.github.haloperidozz.obfuscator.ui.model.SharedTextGeneratorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedTextGeneratorHolder(
    private val textGeneratorRepository: TextGeneratorRepository
) {
    private val initialState: SharedTextGeneratorUiState by lazy {
        val firstGenerator = textGeneratorRepository.all().first()

        SharedTextGeneratorUiState(
            currentGenerator = firstGenerator,
            generatorValue = firstGenerator.defaultValue()
        )
    }

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<SharedTextGeneratorUiState> = _uiState.asStateFlow()

    fun selectGenerator(generatorInfo: TextGeneratorInfo<*>) {
        _uiState.update { currentState ->
            currentState.copy(
                currentGenerator = generatorInfo,
                generatorValue = generatorInfo.defaultValue()
            )
        }
    }

    fun updateGeneratorValue(generatorValue: TextGeneratorValue) {
        _uiState.update { currentState ->
            currentState.copy(generatorValue = generatorValue)
        }
    }
}
