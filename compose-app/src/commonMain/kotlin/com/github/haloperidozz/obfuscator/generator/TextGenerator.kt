package com.github.haloperidozz.obfuscator.generator

interface TextGenerator<T> {
    fun generate(input: String, value: T): String
}