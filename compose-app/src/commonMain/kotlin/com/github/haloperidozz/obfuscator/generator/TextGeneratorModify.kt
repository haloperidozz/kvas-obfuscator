package com.github.haloperidozz.obfuscator.generator

import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator

inline fun FloatTextGenerator.modify(
    crossinline customGenerate: (
        generator: TextGenerator<Float>, input: String, value: Float
    ) -> String
) = object : FloatTextGenerator() {
    override val meta: TextGeneratorMeta = this@modify.meta

    override fun doGenerate(input: String, value: Float): String {
        return customGenerate(this@modify, input, value)
    }
}

inline fun SelectTextGenerator.modify(
    crossinline customGenerate: (
        generator: TextGenerator<Int>, input: String, selected: String, index: Int
    ) -> String
) = object : SelectTextGenerator() {
    override val meta: TextGeneratorMeta = this@modify.meta

    override fun generate(input: String, selected: String, index: Int): String {
        return customGenerate(this@modify, input, selected, index)
    }
}

inline fun SimpleTextGenerator.modify(
    crossinline customGenerate: (generator: TextGenerator<Unit>, input: String) -> String
) = object : SimpleTextGenerator() {
    override val meta: TextGeneratorMeta = this@modify.meta

    override fun generate(input: String): String {
        return customGenerate(this@modify, input)
    }
}

inline fun StringTextGenerator.modify(
    crossinline customGenerate: (
        generator: TextGenerator<String>, input: String, value: String
    ) -> String
) = object : StringTextGenerator {
    override val meta: TextGeneratorMeta = this@modify.meta

    override fun generate(input: String, value: String): String {
        return customGenerate(this@modify, input, value)
    }
}