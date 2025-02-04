@file:OptIn(ExperimentalEncodingApi::class)

package com.github.haloperidozz.obfuscator.repository

import com.github.haloperidozz.obfuscator.generator.TextGenerator
import com.github.haloperidozz.obfuscator.generator.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.generator.TextGeneratorMeta
import com.github.haloperidozz.obfuscator.generator.impl.*
import com.github.haloperidozz.obfuscator.generator.modify
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.util.ISO9
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class TextGeneratorRepositoryImpl : TextGeneratorRepository {
    private val generatorMap = GENERATORS.associateBy { it.meta.id }

    override fun get(id: String): TextGenerator<*>? = generatorMap[id]

    override fun contains(id: String): Boolean = generatorMap.containsKey(id)

    override fun all(): List<TextGenerator<*>> = GENERATORS

    companion object {
        val GENERATORS = buildList<TextGenerator<*>> {
            addAll(
                listOf(
                    CaesarCipherTextGenerator(),
                    AtbashCipherTextGenerator(),
                    TitloTextGenerator(),
                    ZalgoTextGenerator()
                )
            )

            add(
                AssemblyTextGenerator().modify { generator, input ->
                    generator.generate(ISO9.Simple.transliterate(input), Unit)
                }
            )

            add(
                BrainfuckTextGenerator().modify { generator, input ->
                    generator.generate(ISO9.Simple.transliterate(input), Unit)
                }
            )

            add(
                object : SimpleTextGenerator() {
                    override val meta: TextGeneratorMeta = TextGeneratorMeta(
                        id = "iso9",
                        category = TextGeneratorCategory.Script
                    )

                    override fun generate(input: String): String {
                        return ISO9.Standard.transliterate(input)
                    }
                }
            )

            add(
                object : SimpleTextGenerator() {
                    override val meta: TextGeneratorMeta = TextGeneratorMeta(
                        id = "base64",
                        category = TextGeneratorCategory.Converter
                    )

                    override fun generate(input: String): String {
                        return Base64.Default.encode(input.encodeToByteArray())
                    }
                }
            )
        }
    }
}