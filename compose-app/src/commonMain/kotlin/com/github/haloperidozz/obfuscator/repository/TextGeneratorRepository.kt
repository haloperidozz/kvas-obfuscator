package com.github.haloperidozz.obfuscator.repository

import com.github.haloperidozz.obfuscator.model.generator.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.model.generator.TextGeneratorInfo

interface TextGeneratorRepository {
    operator fun get(id: String): TextGeneratorInfo<*>?

    operator fun contains(id: String): Boolean

    fun all(): List<TextGeneratorInfo<*>>

    fun byCategory(category: TextGeneratorCategory): List<TextGeneratorInfo<*>>
}