package com.github.haloperidozz.obfuscator.generator

fun interface TextGenerator<T> {
    fun generate(input: String, value: T): String
}