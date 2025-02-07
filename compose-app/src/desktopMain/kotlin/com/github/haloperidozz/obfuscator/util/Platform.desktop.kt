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

import androidx.compose.ui.awt.ComposeWindow
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import javax.swing.JFileChooser

actual class Platform(
    private val composeWindow: ComposeWindow,
    actual val externalEvents: Flow<ExternalEvent>
) {
    actual val type: PlatformType = PlatformType.Desktop

    actual fun saveToFile(text: String, defaultFileName: String) {
        val fileChooser = JFileChooser().apply {
            isVisible = true
            selectedFile = File(defaultFileName)
            dialogType = JFileChooser.SAVE_DIALOG
        }

        val userOption = fileChooser.showSaveDialog(composeWindow)

        if (userOption == JFileChooser.APPROVE_OPTION) {
            Files.write(
                fileChooser.selectedFile.toPath(),
                text.toByteArray(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            )
        }
    }

    actual fun share(text: String) {
        // No-op: Share functionality is not supported on desktop
    }
}
