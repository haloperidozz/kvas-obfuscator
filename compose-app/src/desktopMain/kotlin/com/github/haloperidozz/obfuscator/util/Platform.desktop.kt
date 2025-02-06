package com.github.haloperidozz.obfuscator.util

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.awt.FileDialog
import java.awt.Frame
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

actual class Platform {
    private val _events = MutableSharedFlow<ExternalEvent>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1,
    )

    actual val type: PlatformType = PlatformType.Desktop
    actual val externalEvents: Flow<ExternalEvent> = _events.asSharedFlow()

    actual fun saveToFile(text: String, defaultFileName: String) {
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

    actual fun share(text: String) {
        // No-op: Share functionality is not supported on desktop
    }

    @Composable
    actual fun dynamicColorScheme(darkTheme: Boolean): ColorScheme? {
        return null
    }

    fun produceEvent(event: ExternalEvent) {
        _events.tryEmit(event)
    }
}