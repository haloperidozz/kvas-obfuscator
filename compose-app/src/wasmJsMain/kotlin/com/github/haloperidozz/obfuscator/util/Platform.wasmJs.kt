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

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

actual class Platform {
    actual val type: PlatformType = PlatformType.Web
    actual val externalEvents: Flow<ExternalEvent> = emptyFlow()

    actual fun saveToFile(text: String, defaultFileName: String) {
        // TODO?
    }

    actual fun share(text: String) {
        // No-op: Share functionality is not supported on web
    }

    @Composable
    actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        return null
    }
}
