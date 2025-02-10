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
package com.github.haloperidozz.obfuscator.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.haloperidozz.obfuscator.buildconfig.BuildConfig
import com.github.haloperidozz.obfuscator.util.ExternalEvent
import com.github.haloperidozz.obfuscator.util.LocalPlatform
import kvas_obfuscator.compose_app.generated.resources.*
import kvas_obfuscator.compose_app.generated.resources.Res
import kvas_obfuscator.compose_app.generated.resources.app_icon
import kvas_obfuscator.compose_app.generated.resources.app_name
import kvas_obfuscator.compose_app.generated.resources.info_version
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun InfoScreen(switchScreen: (Screen) -> Unit) {
    val platform = LocalPlatform.current
    val listState = rememberLazyListState()

    LaunchedEffect(platform) {
        platform.externalEvents.collect { event ->
            when (event) {
                ExternalEvent.Back -> switchScreen(Screen.Main)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { switchScreen(Screen.Main) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = listState,
            contentPadding = PaddingValues(bottom = 200.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InfoScreenHeader(
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
            item {
                InfoScreenInfo()
            }
        }
    }
}

@Composable
private fun InfoScreenHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.app_icon),
            contentDescription = "Icon",
            modifier = Modifier
                .sizeIn(maxWidth = 120.dp, maxHeight = 120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun InfoScreenInfo(modifier: Modifier = Modifier) {
    val urlHandler = LocalUriHandler.current

    Column(modifier = modifier) {
        HorizontalDivider()
        InfoListItem(
            headline = stringResource(Res.string.info_version),
            supportingText = BuildConfig.VERSION
        )
        HorizontalDivider()
        InfoListItem(
            headline = stringResource(Res.string.info_github),
            supportingText = BuildConfig.GITHUB_URL,
            modifier = Modifier.clickable {
                urlHandler.openUri(BuildConfig.GITHUB_URL)
            }
        )
        HorizontalDivider()
    }
}

@Composable
private fun InfoListItem(
    headline: String,
    supportingText: String,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(text = headline)
        },
        supportingContent = {
            Text(
                text = supportingText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}
