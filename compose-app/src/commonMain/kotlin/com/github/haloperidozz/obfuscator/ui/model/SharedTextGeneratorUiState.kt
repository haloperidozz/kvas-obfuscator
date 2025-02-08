package com.github.haloperidozz.obfuscator.ui.model

import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorValue

data class SharedTextGeneratorUiState(
    val currentGenerator: TextGeneratorInfo<*>,
    val generatorValue: TextGeneratorValue?
)
