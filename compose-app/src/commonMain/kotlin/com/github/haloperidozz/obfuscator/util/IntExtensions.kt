package com.github.haloperidozz.obfuscator.util

fun Int.toUnicodeCharString() = if (this in 0..0xFFFF) {
    this.toChar().toString()
} else {
    val highSurrogate = ((this - 0x10000) shr 10) + 0xD800
    val lowSurrogate = ((this - 0x10000) and 0x3FF) + 0xDC00

    charArrayOf(highSurrogate.toChar(), lowSurrogate.toChar()).concatToString()
}