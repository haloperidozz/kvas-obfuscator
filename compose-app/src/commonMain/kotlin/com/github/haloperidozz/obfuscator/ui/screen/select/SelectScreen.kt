package com.github.haloperidozz.obfuscator.ui.screen.select

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.github.haloperidozz.obfuscator.ui.screen.Screen
import com.github.haloperidozz.obfuscator.util.ExternalEvent
import com.github.haloperidozz.obfuscator.util.LocalPlatform

@Composable
fun SelectScreen(switchScreen: (Screen) -> Unit) {
    val platform = LocalPlatform.current

    LaunchedEffect(Unit) {
        platform.externalEvents.collect { event ->
            when (event) {
                ExternalEvent.Back -> switchScreen(Screen.Main)
            }
        }
    }

    Button(
        onClick = { switchScreen(Screen.Main) }
    ) {
        Text(text = "Main")
    }
}
