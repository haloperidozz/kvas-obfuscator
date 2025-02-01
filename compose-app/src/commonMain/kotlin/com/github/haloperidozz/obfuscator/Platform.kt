package com.github.haloperidozz.obfuscator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform