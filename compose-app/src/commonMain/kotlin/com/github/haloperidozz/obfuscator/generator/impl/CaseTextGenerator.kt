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
import com.github.haloperidozz.obfuscator.util.TextReplacer
import com.github.haloperidozz.obfuscator.util.transformEach

class CaseTextGenerator : SelectTextGenerator(
    Cases.entries.map { it.caseName }
) {
    override fun generate(input: String, selected: String, index: Int): String {
        return Cases.entries[index].apply(input)
    }

    private enum class Cases(val caseName: String) {
        SMALL_CAPS("sᴍᴀʟʟ ᴄᴀᴘs") {
            override fun apply(input: String): String = SMALL_CAPS_REPLACER(input)
        },
        ALTERNATING("AlTeRnAtInG") {
            override fun apply(input: String): String = buildString {
                var upper = true

                for (char in input) {
                    if (char.isLetter()) {
                        append(if (upper) char.uppercaseChar() else char.lowercaseChar())
                        upper = !upper
                    } else {
                        append(char)
                    }
                }
            }
        },
        TOGGLE_CASE("rEVERSED") {
            override fun apply(input: String): String =
                input.transformEach { char ->
                    when {
                        char.isLowerCase() -> char.uppercaseChar()
                        char.isUpperCase() -> char.lowercaseChar()
                        else -> char
                    }
                }
        },
        UPPERCASE("UPPERCASE") {
            override fun apply(input: String): String = input.uppercase()
        },
        LOWERCASE("lowercase") {
            override fun apply(input: String): String = input.lowercase()
        },
        CAMEL_CASE("CamelCase") {
            override fun apply(input: String): String =
                input.split(SPLIT_REGEX)
                    .filter { it.isNotEmpty() }
                    .joinToString("") { token ->
                        token.lowercase().replaceFirstChar { it.uppercaseChar() }
                    }
        },
        SNAKE_CASE("snake_case") {
            override fun apply(input: String): String =
                input.split(SPLIT_REGEX)
                    .filter { it.isNotEmpty() }
                    .joinToString("_") { token -> token.lowercase() }
        };

        abstract fun apply(input: String): String
    }


    companion object {
        private val SMALL_CAPS_REPLACER = TextReplacer(
            replacements = mapOf(
                "а" to "ᴀ", "б" to "б", "в" to "ʙ", "г" to "ᴦ", "д" to "д",
                "е" to "ᴇ", "ё" to "ᴇ", "ж" to "ж", "з" to "з", "и" to "и",
                "й" to "й", "к" to "ᴋ", "л" to "ᴧ", "м" to "ʍ", "н" to "ʜ",
                "о" to "ᴏ", "п" to "ᴨ", "р" to "ᴘ", "с" to "ᴄ", "т" to "ᴛ",
                "у" to "у", "ф" to "ȹ", "х" to "х", "ц" to "ц", "ч" to "ч",
                "ш" to "ш", "щ" to "щ", "ъ" to "ъ", "ы" to "ы", "ь" to "ь",
                "э" to "϶", "ю" to "ю", "я" to "я",

                "А" to "ᴀ", "Б" to "б", "В" to "ʙ", "Г" to "ᴦ", "Д" to "д",
                "Е" to "ᴇ", "Ё" to "ᴇ", "Ж" to "ж", "З" to "з", "И" to "и",
                "Й" to "й", "К" to "ᴋ", "Л" to "ᴧ", "М" to "ʍ", "Н" to "ʜ",
                "О" to "ᴏ", "П" to "ᴨ", "Р" to "ᴘ", "С" to "ᴄ", "Т" to "ᴛ",
                "У" to "у", "Ф" to "ȹ", "Х" to "х", "Ц" to "ц", "Ч" to "ч",
                "Ш" to "ш", "Щ" to "щ", "Ъ" to "ъ", "Ы" to "ы", "Ь" to "ь",
                "Э" to "϶", "Ю" to "ю", "Я" to "я",

                "a" to "ᴀ", "b" to "ʙ", "c" to "ᴄ", "d" to "ᴅ", "e" to "ᴇ",
                "f" to "ғ", "g" to "ɢ", "h" to "ʜ", "i" to "ɪ", "j" to "ᴊ",
                "k" to "ᴋ", "l" to "ʟ", "m" to "ᴍ", "n" to "ɴ", "o" to "ᴏ",
                "p" to "ᴘ", "q" to "ǫ", "r" to "ʀ", "s" to "s", "t" to "ᴛ",
                "u" to "ᴜ", "v" to "ᴠ", "w" to "ᴡ", "x" to "x", "y" to "ʏ",
                "z" to "ᴢ",

                "A" to "ᴀ", "B" to "ʙ", "C" to "ᴄ", "D" to "ᴅ", "E" to "ᴇ",
                "F" to "ғ", "G" to "ɢ", "H" to "ʜ", "I" to "ɪ", "J" to "ᴊ",
                "K" to "ᴋ", "L" to "ʟ", "M" to "ᴍ", "N" to "ɴ", "O" to "ᴏ",
                "P" to "ᴘ", "Q" to "ǫ", "R" to "ʀ", "S" to "s", "T" to "ᴛ",
                "U" to "ᴜ", "V" to "ᴠ", "W" to "ᴡ", "X" to "x", "Y" to "ʏ",
                "Z" to "ᴢ",

                "0" to "⁰", "1" to "¹", "2" to "²", "3" to "³", "4" to "⁴",
                "5" to "⁵", "6" to "⁶", "7" to "⁷", "8" to "⁸", "9" to "⁹"
            ),
            caseSensitive = false
        )

        private val SPLIT_REGEX = Regex("[\\s_-]+")
    }
}
