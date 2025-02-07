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

import android.content.Context
import androidx.core.app.ShareCompat
import kotlinx.coroutines.flow.Flow

actual class Platform(
    private val context: Context,
    actual val externalEvents: Flow<ExternalEvent>
) {
    actual val type: PlatformType = PlatformType.Android

    actual fun saveToFile(text: String, defaultFileName: String) {
        // TODO?
    }

    actual fun share(text: String) {
        ShareCompat.IntentBuilder(context).apply {
            setText(text)
            setType("text/plain")
            startChooser()
        }
    }
}
