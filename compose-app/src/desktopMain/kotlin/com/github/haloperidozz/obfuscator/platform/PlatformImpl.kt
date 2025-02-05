package com.github.haloperidozz.obfuscator.platform

import androidx.compose.material3.ColorScheme
import java.awt.FileDialog
import java.awt.Frame
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class PlatformImpl : Platform {
    override val type: PlatformType = PlatformType.Desktop

    override fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        return null
    }

    override fun saveToFile(text: String, defaultFileName: String) {
        val fileDialog = FileDialog(null as Frame?, "Save", FileDialog.SAVE)

        fileDialog.file = defaultFileName
        fileDialog.isVisible = true

        if (fileDialog.directory != null && fileDialog.file != null) {
            Files.write(
                Paths.get(fileDialog.directory, fileDialog.file),
                text.toByteArray(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            )
        }
    }

    override fun share(text: String) {
        // No-op: Share functionality is not supported on desktop
    }
}