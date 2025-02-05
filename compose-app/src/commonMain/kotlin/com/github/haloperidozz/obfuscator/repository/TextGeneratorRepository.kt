package com.github.haloperidozz.obfuscator.repository

import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo

interface TextGeneratorRepository {
    operator fun get(id: String): TextGeneratorInfo<*>?

    operator fun contains(id: String): Boolean

    fun all(): List<TextGeneratorInfo<*>>

    fun byCategory(category: TextGeneratorCategory): List<TextGeneratorInfo<*>>
}