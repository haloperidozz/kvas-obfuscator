package com.github.haloperidozz.obfuscator.generator

interface TextGenerator<T> {
    val meta: TextGeneratorMeta

    fun generate(input: String, value: T): String
}