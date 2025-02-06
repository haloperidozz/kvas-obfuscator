package com.github.haloperidozz.obfuscator.di

import com.github.haloperidozz.obfuscator.data.settings.SettingsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val settingsModule = module {
    single<SettingsStorage> { SettingsStorage(androidContext()) }
}