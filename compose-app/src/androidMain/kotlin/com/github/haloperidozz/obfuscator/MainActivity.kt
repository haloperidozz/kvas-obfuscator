package com.github.haloperidozz.obfuscator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.github.haloperidozz.obfuscator.di.commonModules
import com.github.haloperidozz.obfuscator.util.ExternalEvent
import com.github.haloperidozz.obfuscator.util.LocalPlatformProvider
import com.github.haloperidozz.obfuscator.util.Platform
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val externalEvents = MutableSharedFlow<ExternalEvent>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

        setContent {
            val platform = remember(this) { Platform(this, externalEvents) }

            LocalPlatformProvider(platform) {
                KoinApplication(
                    application = {
                        androidContext(this@MainActivity)
                        commonModules()
                    }
                ) {
                    App()
                }
            }
        }

        onBackPressedDispatcher.addCallback {
            externalEvents.tryEmit(ExternalEvent.Back)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}