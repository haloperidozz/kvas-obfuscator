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

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.github.haloperidozz.obfuscator.di.commonModules
import com.github.haloperidozz.obfuscator.ui.theme.AppTheme
import com.github.haloperidozz.obfuscator.util.ExternalEvent
import com.github.haloperidozz.obfuscator.util.LocalPlatformProvider
import com.github.haloperidozz.obfuscator.util.Platform
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
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

            val darkDynamicScheme = remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    dynamicDarkColorScheme(this)
                } else {
                    null
                }
            }

            val lightDynamicScheme = remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    dynamicLightColorScheme(this)
                } else {
                    null
                }
            }

            LocalPlatformProvider(platform) {
                KoinApplication(
                    application = {
                        androidContext(this@MainActivity)
                        commonModules()
                    }
                ) {
                    AppTheme(
                        darkDynamicScheme = darkDynamicScheme,
                        lightDynamicScheme = lightDynamicScheme
                    ) {
                        App()
                    }
                }
            }
        }

        // https://stackoverflow.com/a/79267436
        val transparentBarStyle = SystemBarStyle.light(
            scrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT
        )

        enableEdgeToEdge(
            statusBarStyle = transparentBarStyle,
            navigationBarStyle = transparentBarStyle
        )

        val callback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                externalEvents.tryEmit(ExternalEvent.Back)
            }
        }

        lifecycleScope.launch {
            externalEvents.subscriptionCount.collect { count ->
                callback.isEnabled = count > 0
            }
        }

        onBackPressedDispatcher.addCallback(callback)
    }
}
