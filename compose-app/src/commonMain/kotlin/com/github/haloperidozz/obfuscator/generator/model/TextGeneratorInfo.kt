package com.github.haloperidozz.obfuscator.generator.model

import com.github.haloperidozz.obfuscator.generator.TextGenerator

data class TextGeneratorInfo<T>(
    val id: String,
    val category: TextGeneratorCategory = TextGeneratorCategory.Unknown,
    val instance: TextGenerator<T>
)
