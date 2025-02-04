package com.github.haloperidozz.obfuscator.generators

import com.github.haloperidozz.obfuscator.generator.SelectTextGenerator
import com.github.haloperidozz.obfuscator.generator.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.generator.TextGeneratorMeta
import com.github.haloperidozz.obfuscator.util.Alphabet

class CaesarCipherTextGenerator : SelectTextGenerator(
    Alphabet.RUSSIAN_LOWER.indices.map { index -> "ROT$index" }
) {
    override val meta: TextGeneratorMeta = TextGeneratorMeta(
        id = "caesar-cipher",
        category = TextGeneratorCategory.Cipher
    )

    override fun generate(input: String, selected: String, index: Int): String {
        return input.map { char -> shiftChar(char, index) }.joinToString("")
    }

    private fun shiftChar(char: Char, shift: Int) = when (char) {
        in Alphabet.ENGLISH_LOWER -> shiftChar(char, shift, Alphabet.ENGLISH_LOWER)
        in Alphabet.ENGLISH_UPPER -> shiftChar(char, shift, Alphabet.ENGLISH_UPPER)
        in Alphabet.RUSSIAN_LOWER -> shiftChar(char, shift, Alphabet.RUSSIAN_LOWER)
        in Alphabet.RUSSIAN_UPPER -> shiftChar(char, shift, Alphabet.RUSSIAN_UPPER)
        else -> char
    }

    private fun shiftChar(char: Char, shift: Int, alphabet: String): Char {
        val currentIndex = alphabet.indexOf(char)
        return alphabet[(currentIndex + shift) % alphabet.length]
    }
}