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

fun Int.toUnicodeCharString() = if (this in 0..0xFFFF) {
    this.toChar().toString()
} else {
    val highSurrogate = ((this - 0x10000) shr 10) + 0xD800
    val lowSurrogate = ((this - 0x10000) and 0x3FF) + 0xDC00

    charArrayOf(highSurrogate.toChar(), lowSurrogate.toChar()).concatToString()
}

inline fun <R> String.transformEach(
    separator: String = "",
    charTransformer: (Char) -> R,
): String = this.map(charTransformer).joinToString(separator)

fun ByteArray.toInt(offset: Int = 0): Int {
    return ((this[offset + 0].toInt() and 0xff) shl 24) or
            ((this[offset + 1].toInt() and 0xff) shl 16) or
            ((this[offset + 2].toInt() and 0xff) shl 8) or
            ((this[offset + 3].toInt() and 0xff) shl 0)
}

fun Int.copyBytesTo(destination: ByteArray, offset: Int = 0) {
    destination[offset + 0] = (this ushr 24).toByte()
    destination[offset + 1] = (this ushr 16).toByte()
    destination[offset + 2] = (this ushr 8).toByte()
    destination[offset + 3] = this.toByte()
}
