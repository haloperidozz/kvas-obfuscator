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