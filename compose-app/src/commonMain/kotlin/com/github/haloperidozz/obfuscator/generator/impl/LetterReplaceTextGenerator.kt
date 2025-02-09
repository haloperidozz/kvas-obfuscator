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
package com.github.haloperidozz.obfuscator.generator.impl

import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.util.Alphabet
import com.github.haloperidozz.obfuscator.util.transformEach

class LetterReplaceTextGenerator(
    private val vowel: String,
    private val consonant: String,
    private val softAndHardSign: String
) : SimpleTextGenerator() {
    override fun generate(input: String): String {
        return input.transformEach { char ->
            when (char) {
                in Alphabet.ALL_VOWELS -> vowel
                in Alphabet.ALL_CONSONANTS -> consonant
                in Alphabet.RUSSIAN_SOFT_AND_HARD_SIGNS -> softAndHardSign
                else -> char.toString()
            }
        }
    }
}
