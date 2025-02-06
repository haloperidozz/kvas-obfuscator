package com.github.haloperidozz.obfuscator.di

import org.koin.core.KoinApplication

fun KoinApplication.commonModules() {
    modules(repositoryModule, settingsModule)
}