package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.data.favorite.FavoriteRepositoryImpl
import com.github.haloperidozz.obfuscator.data.generator.TextGeneratorRepositoryImpl
import com.github.haloperidozz.obfuscator.repository.FavoriteRepository
import com.github.haloperidozz.obfuscator.repository.TextGeneratorRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TextGeneratorRepository> { TextGeneratorRepositoryImpl() }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
}