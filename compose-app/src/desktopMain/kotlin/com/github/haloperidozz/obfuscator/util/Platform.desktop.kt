package com.github.haloperidozz.obfuscator.util

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
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

    @Composable
    actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        return null
    }
}