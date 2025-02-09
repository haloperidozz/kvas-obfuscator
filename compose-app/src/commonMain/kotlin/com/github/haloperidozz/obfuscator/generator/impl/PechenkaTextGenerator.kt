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
import com.github.haloperidozz.obfuscator.util.toUnicodeCharString
import com.github.haloperidozz.obfuscator.util.transformEach

class PechenkaTextGenerator : SimpleTextGenerator() {
    override fun generate(input: String): String = input.transformEach {
        UNICODE_RANGES.random().random().toUnicodeCharString()
    }

    companion object {
        private val UNICODE_RANGES = listOf(
            0x2700..0x27BF,         // Dingbats
            0xFB50..0xFDFF,         // Arabic Presentation Forms-A
            0x12000..0x123FF,       // Cuneiform
            0x12400..0x1247F,       // Cuneiform Numbers and Punctuation
            0x13000..0x1342F,       // Egyptian Hieroglyphs
        )
    }
}
