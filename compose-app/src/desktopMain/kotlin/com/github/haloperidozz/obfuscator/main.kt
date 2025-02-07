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
package com.github.haloperidozz.obfuscator

import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.haloperidozz.obfuscator.di.commonModules
import com.github.haloperidozz.obfuscator.ui.theme.AppTheme
import com.github.haloperidozz.obfuscator.util.ExternalEvent
import com.github.haloperidozz.obfuscator.util.LocalPlatformProvider
import com.github.haloperidozz.obfuscator.util.Platform
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.compose.KoinApplication

fun main() = application {
    val externalEvents = remember {
        MutableSharedFlow<ExternalEvent>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "kvas-obfuscator",
        onKeyEvent = {
            if (it.type == KeyEventType.KeyUp && it.key == Key.Escape) {
                externalEvents.tryEmit(ExternalEvent.Back)
            }
            false
        },
    ) {
        LocalPlatformProvider(remember { Platform(window, externalEvents) }) {
            KoinApplication(application = { commonModules() }) {
                AppTheme { App() }
            }
        }
    }
}
