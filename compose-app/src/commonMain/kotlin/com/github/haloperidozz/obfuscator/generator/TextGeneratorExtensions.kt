package com.github.haloperidozz.obfuscator.generator

fun <T> TextGenerator<T>.modify(
    customGenerate: (generator: TextGenerator<T>, input: String, value: T) -> String
): TextGenerator<T> {
    return TextGenerator { input, value -> customGenerate(this@modify, input, value) }
}