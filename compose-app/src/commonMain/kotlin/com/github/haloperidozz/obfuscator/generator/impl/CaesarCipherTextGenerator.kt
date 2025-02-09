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

import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.util.Alphabet
import com.github.haloperidozz.obfuscator.util.transformEach

class CaesarCipherTextGenerator : SelectTextGenerator(
    Alphabet.RUSSIAN_LOWER.indices.map { index -> "ROT$index" }
) {
    override fun generate(input: String, selected: String, index: Int): String {
        return input.transformEach { char -> shiftChar(char, index) }
    }

    private fun shiftChar(char: Char, shift: Int) = when (char) {
        in Alphabet.ENGLISH_LOWER -> shiftChar(char, shift, Alphabet.ENGLISH_LOWER)
        in Alphabet.ENGLISH_UPPER -> shiftChar(char, shift, Alphabet.ENGLISH_UPPER)
        in Alphabet.RUSSIAN_LOWER -> shiftChar(char, shift, Alphabet.RUSSIAN_LOWER)
        in Alphabet.RUSSIAN_UPPER -> shiftChar(char, shift, Alphabet.RUSSIAN_UPPER)
        else -> char
    }

    private fun shiftChar(char: Char, shift: Int, alphabet: String): Char {
        val currentIndex = alphabet.indexOf(char)
        return alphabet[(currentIndex + shift) % alphabet.length]
    }
}
