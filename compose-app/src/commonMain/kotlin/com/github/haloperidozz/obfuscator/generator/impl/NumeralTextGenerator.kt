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

class NumeralTextGenerator : SelectTextGenerator(
    NumeralBase.entries.map { it.name }
) {
    override fun generate(input: String, selected: String, index: Int): String {
        val base = NumeralBase.entries[index]
        return input.encodeToByteArray().joinToString(" ") { base.format(it) }
    }

    private enum class NumeralBase(private val base: Int) {
        BIN(2),
        OCT(8),
        DEC(10),
        HEX(16);

        fun format(byte: Byte): String {
            val result = byte.toUByte().toString(base)

            return when (this) {
                BIN -> result.padStart(8, '0')
                else -> result
            }
        }
    }
}
