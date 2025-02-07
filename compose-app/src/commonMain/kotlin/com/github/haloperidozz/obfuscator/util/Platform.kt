/*
 * Copyright (C) 2025 haloperidozz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.github.haloperidozz.obfuscator.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.Flow

enum class ExternalEvent {
    Back
}

enum class PlatformType {
    Desktop,
    Android,
    Web
}

expect class Platform {
    val type: PlatformType

    val externalEvents: Flow<ExternalEvent>

    fun saveToFile(text: String, defaultFileName: String = "output.txt")

    fun share(text: String)
}

@Composable
fun LocalPlatformProvider(platform: Platform, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalPlatform provides platform) {
        content()
    }
}

val LocalPlatform = staticCompositionLocalOf<Platform> {
    error("no provided")
}
