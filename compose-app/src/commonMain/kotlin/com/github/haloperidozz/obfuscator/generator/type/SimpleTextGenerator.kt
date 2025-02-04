package com.github.haloperidozz.obfuscator.generator.type

import com.github.haloperidozz.obfuscator.generator.TextGenerator

abstract class SimpleTextGenerator : TextGenerator<Unit> {
    final override fun generate(input: String, value: Unit): String {
        return generate(input)
    }

    protected abstract fun generate(input: String): String
}