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
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorValue
import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator
import com.github.haloperidozz.obfuscator.ui.model.MainScreenUiState
import com.github.haloperidozz.obfuscator.ui.viewmodel.shared.SharedTextGeneratorHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val sharedTextGeneratorHolder: SharedTextGeneratorHolder
) : ViewModel() {
    private val initialState: MainScreenUiState by lazy {
        val sharedUiState = sharedTextGeneratorHolder.uiState.value

        MainScreenUiState(
            currentText = "",
            currentGeneratedText = "",
            currentGenerator = sharedUiState.currentGenerator,
            generatorValue = sharedUiState.generatorValue
        )
    }

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            sharedTextGeneratorHolder.uiState.collect { sharedState ->
                _uiState.update { currentState ->
                    currentState.copy(
                        currentGenerator = sharedState.currentGenerator,
                        generatorValue = sharedState.generatorValue
                    )
                }

                updateText()
            }
        }
    }

    fun updateText(newText: String? = null) {
        _uiState.update { currentState ->
            val textToUse = newText ?: currentState.currentText

            currentState.copy(
                currentText = textToUse,
                currentGeneratedText = generateText(textToUse)
            )
        }
    }

    fun updateGeneratorValue(generatorValue: TextGeneratorValue) {
        sharedTextGeneratorHolder.updateGeneratorValue(generatorValue)
    }

    private fun generateText(text: String): String {
        val currentState = uiState.value

        val generatorInfo = currentState.currentGenerator
        val currentValue = currentState.generatorValue

        return when (val generator = generatorInfo.instance) {
            is FloatTextGenerator -> {
                val value = (currentValue as? TextGeneratorValue.FloatValue)
                    ?: error("Expected a FloatValue for FloatTextGenerator")
                generator.doGenerate(text, value.value)
            }
            is SelectTextGenerator -> {
                val value = (currentValue as? TextGeneratorValue.SelectValue)
                    ?: error("Expected a SelectValue for SelectTextGenerator")
                generator.generate(text, value.index)
            }
            is SimpleTextGenerator -> {
                generator.generate(text, Unit)
            }
            is StringTextGenerator -> {
                val value = (currentValue as? TextGeneratorValue.StringValue)
                    ?: error("Expected a StringValue for StringTextGenerator")
                generator.generate(text, value.value)
            }
            else -> ""
        }
    }
}
