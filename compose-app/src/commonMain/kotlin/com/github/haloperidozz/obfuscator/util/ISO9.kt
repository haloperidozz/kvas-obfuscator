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
package com.github.haloperidozz.obfuscator.util

// https://ru.wikipedia.org/wiki/ISO_9
enum class ISO9 {
    Simple {
        override fun transliterate(text: String): String {
            return SIMPLE_MAPPING(text)
        }
    },
    Standard {
        override fun transliterate(text: String): String {
            return STANDARD_MAPPING(text).replace(C_REGEX) { match ->
                val c = match.groupValues[1]
                val following = match.groupValues[2]

                if (following.isNotEmpty()) {
                    if (c == "Ц") "C$following" else "c$following"
                } else {
                    if (c == "Ц") "CZ" else "cz"
                }
            }
        }
    };

    abstract fun transliterate(text: String): String

    companion object {
        private val SIMPLE_MAPPING = TextReplacer(
            replacements = mapOf(
                "а" to "a", "б" to "b", "в" to "v", "г" to "g", "д" to "d",
                "е" to "e", "ё" to "yo", "ж" to "j", "з" to "z", "и" to "i",
                "й" to "y", "к" to "k", "л" to "l", "м" to "m", "н" to "n",
                "о" to "o", "п" to "p", "р" to "r", "с" to "s", "т" to "t",
                "у" to "u", "ф" to "f", "х" to "h", "ц" to "c", "ч" to "ch",
                "ш" to "sh", "щ" to "sch", "ъ" to "", "ы" to "i", "ь" to "'",
                "э" to "e", "ю" to "yu", "я" to "ya"
            ),
            caseSensitive = false
        )

        private val STANDARD_MAPPING = TextReplacer(
            replacements = mapOf(
                "а" to "a", "б" to "b", "в" to "v", "г" to "g", "д" to "d",
                "е" to "e", "ё" to "yo", "ж" to "zh", "з" to "z", "и" to "i",
                "й" to "j", "к" to "k", "л" to "l", "м" to "m", "н" to "n",
                "о" to "o", "п" to "p", "р" to "r", "с" to "s", "т" to "t",
                "у" to "u", "ф" to "f", "х" to "x",

                // Keep "ц" unchanged for special processing.
                "ц" to "ц",

                "ч" to "ch", "ш" to "sh", "щ" to "shh", "ъ" to "``", "ы" to "y`",
                "ь" to "`", "э" to "e`", "ю" to "yu", "я" to "ya",
                "’" to "'"
            ),
            caseSensitive = false
        )

        // It is recommended to use "C" before the letters I, E, Y, J
        // and "CZ" otherwise.
        private val C_REGEX = Regex("([Цц])([IEYJieyj])?")
    }
}
