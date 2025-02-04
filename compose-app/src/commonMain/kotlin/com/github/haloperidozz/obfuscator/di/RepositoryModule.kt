package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<TextGeneratorRepository> { TextGeneratorRepositoryImpl(getAll()) }
}