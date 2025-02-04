package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.generator.TextGenerator
import com.github.haloperidozz.obfuscator.generator.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.generator.TextGeneratorMeta
import com.github.haloperidozz.obfuscator.generator.impl.*
import com.github.haloperidozz.obfuscator.generator.modify
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.util.ISO9
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
val textGeneratorModule = module(createdAtStart = true) {
    single<TextGenerator<*>>(named("assembly")) {
        AssemblyTextGenerator().modify { generator, input ->
            generator.generate(ISO9.Simple.transliterate(input), Unit)
        }
    }
    single<TextGenerator<*>>(named("atbash-cipher")) { AtbashCipherTextGenerator() }
    single<TextGenerator<*>>(named("brainfuck")) {
        BrainfuckTextGenerator().modify { generator, input ->
            generator.generate(ISO9.Simple.transliterate(input), Unit)
        }
    }
    single<TextGenerator<*>>(named("caesar-cipher")) { CaesarCipherTextGenerator() }
    single<TextGenerator<*>>(named("titlo")) { TitloTextGenerator() }
    single<TextGenerator<*>>(named("zalgo")) { ZalgoTextGenerator() }
    single<TextGenerator<*>>(named("iso9")) {
        object : SimpleTextGenerator() {
            override val meta: TextGeneratorMeta = TextGeneratorMeta(
                id = "iso9",
                category = TextGeneratorCategory.Script
            )

            override fun generate(input: String): String {
                return ISO9.Standard.transliterate(input)
            }
        }
    }
    single<TextGenerator<*>>(named("base64")) {
        object : SimpleTextGenerator() {
            override val meta: TextGeneratorMeta = TextGeneratorMeta(
                id = "base64",
                category = TextGeneratorCategory.Converter
            )

            override fun generate(input: String): String {
                return Base64.Default.encode(input.encodeToByteArray())
            }
        }
    }
}