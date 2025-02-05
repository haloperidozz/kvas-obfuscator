package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.data.favorite.FavoriteDataSource
import com.github.haloperidozz.obfuscator.data.favorite.FavoriteDataSourceImpl
import com.github.haloperidozz.obfuscator.platform.Platform
import com.github.haloperidozz.obfuscator.platform.PlatformImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<FavoriteDataSource> { FavoriteDataSourceImpl(androidContext()) }
    single<Platform> { PlatformImpl(androidContext()) }
}