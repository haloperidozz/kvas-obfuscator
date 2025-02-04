package com.github.haloperidozz.obfuscator.repository

import com.github.haloperidozz.obfuscator.generator.TextGenerator

class TextGeneratorRepositoryImpl(
    private val generators: List<TextGenerator<*>>
) : TextGeneratorRepository {
    private val generatorMap = generators.associateBy { it.meta.id }

    override fun get(id: String): TextGenerator<*>? = generatorMap[id]

    override fun contains(id: String): Boolean = generatorMap.containsKey(id)

    override fun all(): List<TextGenerator<*>> = generators
}