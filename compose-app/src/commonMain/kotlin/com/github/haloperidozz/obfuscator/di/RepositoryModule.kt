package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.data.favorite.FavoriteTextGeneratorRepositoryImpl
import com.github.haloperidozz.obfuscator.data.generator.TextGeneratorRepositoryImpl
import com.github.haloperidozz.obfuscator.repository.FavoriteTextGeneratorRepository
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TextGeneratorRepository> { TextGeneratorRepositoryImpl() }
    single<FavoriteTextGeneratorRepository> {
        FavoriteTextGeneratorRepositoryImpl(get(), get())
    }
}