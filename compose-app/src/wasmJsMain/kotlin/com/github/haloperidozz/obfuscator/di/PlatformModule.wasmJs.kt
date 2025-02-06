package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.data.favorite.FavoriteDataSource
import com.github.haloperidozz.obfuscator.data.favorite.FavoriteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<FavoriteDataSource> { FavoriteDataSourceImpl() }
}