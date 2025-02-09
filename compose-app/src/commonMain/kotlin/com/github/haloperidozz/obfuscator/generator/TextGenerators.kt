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
package com.github.haloperidozz.obfuscator.generator

import com.github.haloperidozz.obfuscator.generator.impl.*
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorValue
import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator
import com.github.haloperidozz.obfuscator.util.ISO9
import kotlin.io.encoding.ExperimentalEncodingApi

enum class TextGenerators(
    val category: TextGeneratorCategory = TextGeneratorCategory.Unknown,
    val instance: TextGenerator<*>
) {
    CaesarCipher(
        category = TextGeneratorCategory.Cipher,
        instance = CaesarCipherTextGenerator()
    ),
    AtbashCipher(
        category = TextGeneratorCategory.Cipher,
        instance = AtbashCipherTextGenerator()
    ),
    Titlo(
        category = TextGeneratorCategory.Other,
        instance = TitloTextGenerator()
    ),
    Zalgo(
        category = TextGeneratorCategory.Other,
        instance = ZalgoTextGenerator()
    ),
    Assembly(
        category = TextGeneratorCategory.Programming,
        instance = AssemblyTextGenerator().modify { generator, input ->
            generator.generate(ISO9.Simple.transliterate(input), Unit)
        }
    ),
    Brainfuck(
        category = TextGeneratorCategory.Programming,
        instance = BrainfuckTextGenerator().modify { generator, input ->
            generator.generate(ISO9.Simple.transliterate(input), Unit)
        }
    ),
    Translit(
        category = TextGeneratorCategory.Script,
        instance = object : SimpleTextGenerator() {
            override fun generate(input: String): String {
                return ISO9.Standard.transliterate(input)
            }
        }
    ),
    @OptIn(ExperimentalEncodingApi::class)
    Base64(
        category = TextGeneratorCategory.Converter,
        instance = object : SimpleTextGenerator() {
            override fun generate(input: String): String {
                return kotlin.io.encoding.Base64.encode(input.encodeToByteArray())
            }
        }
    );

    fun defaultValue(): TextGeneratorValue? {
        return when (instance) {
            is FloatTextGenerator -> {
                val defaultValue = instance.range.endInclusive / 2.0f
                TextGeneratorValue.FloatValue(defaultValue)
            }
            is StringTextGenerator -> TextGeneratorValue.StringValue("")
            is SelectTextGenerator -> TextGeneratorValue.SelectValue(0)
            else -> null
        }
    }
}
