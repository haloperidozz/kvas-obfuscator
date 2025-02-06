package com.github.haloperidozz.obfuscator.generator

/**
 * WARNING: Do **NOT** implement this interface directly.
 *
 * Instead, use one of the provided implementations:
 * - [com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator]
 * - [com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator]
 * - [com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator]
 * - [com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator]
 */
interface TextGenerator<T> {
    fun generate(input: String, value: T): String
}