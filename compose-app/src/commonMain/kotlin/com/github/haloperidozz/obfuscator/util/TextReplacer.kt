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
    private val root = TrieNode()

    init {
        for ((pattern, replacement) in replacements) {
            var node = root
            val key = if (caseSensitive) pattern else pattern.lowercase()

            for (char in key) {
                node = node.children.getOrPut(char) { TrieNode() }
            }

            node.isEndOfPattern = true
            node.replacement = replacement
        }
    }

    fun replace(input: String): String = buildString {
        var index = 0

        while (index < input.length) {
            val (matchLength, replacement) = findLongestMatch(input, index)

            if (matchLength > 0) {
                if (isNotEmpty()) append(delimiter)
                append(replacement)
                index += matchLength
            } else {
                append(input[index])
                index++
            }
        }
    }

    private fun findLongestMatch(input: String, index: Int): Pair<Int, String> {
        var node = root
        var longestMatchLength = 0
        var replacement: String? = null
        var currentIndex = index

        while (currentIndex < input.length) {
            val char = if (caseSensitive) {
                input[currentIndex]
            } else {
                input[currentIndex].lowercaseChar()
            }

            node = node.children[char] ?: break

            if (node.isEndOfPattern) {
                longestMatchLength = currentIndex - index + 1
                replacement = node.replacement
            }

            currentIndex++
        }

        return if (longestMatchLength > 0 && replacement != null) {
            longestMatchLength to replacement
        } else {
            0 to ""
        }
    }

    operator fun invoke(input: String): String = replace(input)

    private data class TrieNode(
        val children: MutableMap<Char, TrieNode> = mutableMapOf(),
        var replacement: String? = null,
        var isEndOfPattern: Boolean = false
    )
}
