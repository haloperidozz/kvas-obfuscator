package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.generator.TextGenerator
import com.github.haloperidozz.obfuscator.generator.impl.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val textGeneratorModule = module(createdAtStart = true) {
    single<TextGenerator<*>>(named("assembly")) { AssemblyTextGenerator() }
    single<TextGenerator<*>>(named("atbash-cipher")) { AtbashCipherTextGenerator() }
    single<TextGenerator<*>>(named("brainfuck")) { BrainfuckTextGenerator() }
    single<TextGenerator<*>>(named("caesar-cipher")) { CaesarCipherTextGenerator() }
    single<TextGenerator<*>>(named("titlo")) { TitloTextGenerator() }
    single<TextGenerator<*>>(named("zalgo")) { ZalgoTextGenerator() }
}