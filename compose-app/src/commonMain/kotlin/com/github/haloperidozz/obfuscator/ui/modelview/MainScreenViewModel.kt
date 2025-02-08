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
package com.github.haloperidozz.obfuscator.ui.modelview

import androidx.lifecycle.ViewModel
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorValue
import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import com.github.haloperidozz.obfuscator.ui.model.MainScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenViewModel(
    private val generatorRepository: TextGeneratorRepository
) : ViewModel() {
    private val initialState: MainScreenUiState by lazy {
        val firstGenerator = generatorRepository.all().first()
        MainScreenUiState(
            currentText = "",
            currentGeneratedText = "",
            currentGenerator = firstGenerator,
            generatorValue = firstGenerator.defaultValue()
        )
    }

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    fun updateText(newText: String? = null) {
        _uiState.update { currentState ->
            val textToUse = newText ?: currentState.currentText

            currentState.copy(
                currentText = textToUse,
                currentGeneratedText = generateText(textToUse)
            )
        }
    }

    fun selectGenerator(generatorInfo: TextGeneratorInfo<*>) {
        _uiState.update { currentState ->
            currentState.copy(
                currentGenerator = generatorInfo,
                generatorValue = generatorInfo.defaultValue()
            )
        }
        updateText()
    }

    fun updateGeneratorValue(generatorValue: TextGeneratorValue) {
        _uiState.update { currentState ->
            currentState.copy(generatorValue = generatorValue)
        }
        updateText()
    }

    private fun generateText(text: String): String {
        val state = _uiState.value
        val currentValue = state.generatorValue

        return when (val generator = state.currentGenerator.instance) {
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
