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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.haloperidozz.obfuscator.ui.screen.Screen
import com.github.haloperidozz.obfuscator.ui.screen.InfoScreen
import com.github.haloperidozz.obfuscator.ui.screen.MainScreen
import com.github.haloperidozz.obfuscator.ui.screen.SelectScreen

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Main) }
    val switchScreen: (Screen) -> Unit = { screen -> currentScreen = screen }

    Crossfade(
        targetState = currentScreen,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { screen ->
        when (screen) {
            Screen.Main -> MainScreen(switchScreen)
            Screen.Info -> InfoScreen(switchScreen)
            Screen.Select -> SelectScreen(switchScreen)
        }
    }
}
