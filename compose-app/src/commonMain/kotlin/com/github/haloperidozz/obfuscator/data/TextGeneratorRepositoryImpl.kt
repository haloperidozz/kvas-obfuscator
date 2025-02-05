package com.github.haloperidozz.obfuscator.data

import com.github.haloperidozz.obfuscator.generator.impl.*
import com.github.haloperidozz.obfuscator.generator.modify
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.model.generator.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.model.generator.TextGeneratorInfo
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import com.github.haloperidozz.obfuscator.util.ISO9
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class TextGeneratorRepositoryImpl : TextGeneratorRepository {
    @OptIn(ExperimentalEncodingApi::class)
    private val generators: List<TextGeneratorInfo<*>> = listOf(
        TextGeneratorInfo(
            id = "caesar-cipher",
            category = TextGeneratorCategory.Cipher,
            instance = CaesarCipherTextGenerator()
        ),
        TextGeneratorInfo(
            id = "atbash-cipher",
            category = TextGeneratorCategory.Cipher,
            instance = AtbashCipherTextGenerator()
        ),
        TextGeneratorInfo(
            id = "titlo",
            category = TextGeneratorCategory.Other,
            instance = TitloTextGenerator()
        ),
        TextGeneratorInfo(
            id = "zalgo",
            category = TextGeneratorCategory.Other,
            instance = ZalgoTextGenerator()
        ),
        TextGeneratorInfo(
            id = "assembly",
            category = TextGeneratorCategory.Programming,
            instance = AssemblyTextGenerator().modify { generator, input ->
                generator.generate(ISO9.Simple.transliterate(input), Unit)
            }
        ),
        TextGeneratorInfo(
            id = "brainfuck",
            category = TextGeneratorCategory.Programming,
            instance = BrainfuckTextGenerator().modify { generator, input ->
                generator.generate(ISO9.Simple.transliterate(input), Unit)
            }
        ),
        TextGeneratorInfo(
            id = "iso9",
            category = TextGeneratorCategory.Script,
            instance = object : SimpleTextGenerator() {
                override fun generate(input: String): String {
                    return ISO9.Standard.transliterate(input)
                }
            }
        ),
        TextGeneratorInfo(
            id = "base64",
            category = TextGeneratorCategory.Converter,
            instance = object : SimpleTextGenerator() {
                override fun generate(input: String): String {
                    return Base64.encode(input.encodeToByteArray())
                }
            }
        )
    )

    private val generatorMap = generators.associateBy { generator -> generator.id }

    override operator fun get(id: String): TextGeneratorInfo<*>? = generatorMap[id]

    override operator fun contains(id: String): Boolean = generatorMap.containsKey(id)

    override fun all(): List<TextGeneratorInfo<*>> = generators

    override fun byCategory(category: TextGeneratorCategory): List<TextGeneratorInfo<*>> {
        return all().filter { generator -> generator.category == category }
    }
}