package com.github.haloperidozz.obfuscator.generators

import com.github.haloperidozz.obfuscator.generator.FloatTextGenerator
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