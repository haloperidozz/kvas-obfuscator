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

import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator

inline fun FloatTextGenerator.modify(
    crossinline customGenerate: (
        generator: TextGenerator<Float>, input: String, value: Float
    ) -> String
) = object : FloatTextGenerator() {
    override fun doGenerate(input: String, value: Float): String {
        return customGenerate(this@modify, input, value)
    }
}

inline fun SelectTextGenerator.modify(
    crossinline customGenerate: (
        generator: TextGenerator<Int>, input: String, selected: String, index: Int
    ) -> String
) = object : SelectTextGenerator() {
    override fun generate(input: String, selected: String, index: Int): String {
        return customGenerate(this@modify, input, selected, index)
    }
}

inline fun SimpleTextGenerator.modify(
    crossinline customGenerate: (generator: TextGenerator<Unit>, input: String) -> String
) = object : SimpleTextGenerator() {
    override fun generate(input: String): String {
        return customGenerate(this@modify, input)
    }
}

inline fun StringTextGenerator.modify(
    crossinline customGenerate: (
        generator: TextGenerator<String>, input: String, value: String
    ) -> String
) = object : StringTextGenerator {
    override fun generate(input: String, value: String): String {
        return customGenerate(this@modify, input, value)
    }
}
