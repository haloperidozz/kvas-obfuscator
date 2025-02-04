package com.github.haloperidozz.obfuscator.generators

import com.github.haloperidozz.obfuscator.generator.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.util.Alphabet

class AtbashCipherTextGenerator : SimpleTextGenerator() {
    override fun generate(input: String): String {
        return input.map { char -> reverseChar(char) }.joinToString("")
    }

    private fun reverseChar(char: Char) = when (char) {
        in Alphabet.ENGLISH_LOWER -> reverseChar(char, Alphabet.ENGLISH_LOWER)
        in Alphabet.ENGLISH_UPPER -> reverseChar(char, Alphabet.ENGLISH_UPPER)
        in Alphabet.RUSSIAN_LOWER -> reverseChar(char, Alphabet.RUSSIAN_LOWER)
        in Alphabet.RUSSIAN_UPPER -> reverseChar(char, Alphabet.RUSSIAN_UPPER)
        else -> char
    }

    private fun reverseChar(char: Char, alphabet: String): Char {
        val index = alphabet.indexOf(char)
        return if (index != -1) alphabet[alphabet.lastIndex - index] else char
    }
}