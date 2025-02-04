package com.github.haloperidozz.obfuscator.repository

import com.github.haloperidozz.obfuscator.generator.TextGenerator

interface TextGeneratorRepository {
    operator fun get(id: String): TextGenerator<*>?

    operator fun contains(id: String): Boolean

    fun all(): List<TextGenerator<*>>
}