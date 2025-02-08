package com.github.haloperidozz.obfuscator.ui.modelview.shared

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