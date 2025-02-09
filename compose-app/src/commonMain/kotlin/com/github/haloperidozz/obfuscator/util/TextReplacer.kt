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

class TextReplacer(
    replacements: Map<String, String>,
    private val caseSensitive: Boolean = true,
    private val delimiter: String = ""
) {
    private val replacementMap: Map<String, String> = if (caseSensitive) {
        replacements
    } else {
        replacements.mapKeys { it.key.lowercase() }
    }

    private val sortedReplacements: List<Pair<String, String>> = replacementMap
        .entries
        .map { it.toPair() }
        .sortedByDescending { it.first.length }

    fun replace(input: String): String = buildString {
        var index = 0

        while (index < input.length) {
            val rule = sortedReplacements.firstOrNull { (pattern, _) ->
                if (caseSensitive) {
                    input.startsWith(pattern, index)
                } else {
                    input.regionMatches(
                        ignoreCase = true,
                        thisOffset = index,
                        other = pattern,
                        otherOffset = 0,
                        length = pattern.length
                    )
                }
            }

            if (rule != null) {
                val (pattern, replacement) = rule

                if (isNotEmpty()) append(delimiter)
                append(replacement)
                index += pattern.length
            } else {
                append(input[index])
                index++
            }
        }
    }

    operator fun invoke(input: String): String = replace(input)
}
