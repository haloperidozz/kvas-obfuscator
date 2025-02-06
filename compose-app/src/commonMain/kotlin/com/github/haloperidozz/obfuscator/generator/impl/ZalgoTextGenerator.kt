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

import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.util.toUnicodeCharString

class ZalgoTextGenerator : FloatTextGenerator(range = 1.0f..100.0f) {
    override fun doGenerate(input: String, value: Float): String {
        return input.map { char ->
            char + generateMarks(value.toInt())
        }.joinToString("")
    }

    private fun generateMarks(maxHeight: Int) = buildString {
        append(MIDDLE_MARKS.random())
        repeat((0..maxHeight).random()) { append(ABOVE_MARKS.random()) }
        repeat((0..maxHeight).random()) { append(BELOW_MARKS.random()) }
    }

    companion object {
        // https://en.wikipedia.org/wiki/Combining_Diacritical_Marks

        private val ABOVE_MARKS = listOf(
            (0x300..0x315).map { code -> code.toUnicodeCharString() },
            (0x33D..0x344).map { code -> code.toUnicodeCharString() },
        ).flatten()

        private val MIDDLE_MARKS = listOf(
            (0x334..0x338).map { code -> code.toUnicodeCharString() }
        ).flatten()

        private val BELOW_MARKS = listOf(
            (0x316..0x333).mapNotNull { code ->
                if (code != 0x31A && code != 0x31B) {
                    code.toUnicodeCharString()
                } else null
            },
            (0x339..0x33C).map { code -> code.toUnicodeCharString() },
        ).flatten()
    }
}
