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
import com.github.haloperidozz.obfuscator.util.TextReplacer

class TextReplacerTextGenerator(
    private val replacer: TextReplacer
) : SimpleTextGenerator() {
    constructor(
        replacements: Map<String, String>,
        caseSensitive: Boolean = true,
        delimiter: String = ""
    ) : this(TextReplacer(replacements, caseSensitive, delimiter))

    override fun generate(input: String): String = replacer(input)
}
