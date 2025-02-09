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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.haloperidozz.obfuscator.generator.TextGenerators
import com.github.haloperidozz.obfuscator.ui.viewmodel.SelectScreenViewModel
import com.github.haloperidozz.obfuscator.util.ExternalEvent
import com.github.haloperidozz.obfuscator.util.LocalPlatform
import kvas_obfuscator.compose_app.generated.resources.Res
import kvas_obfuscator.compose_app.generated.resources.select
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectScreen(
    switchScreen: (Screen) -> Unit,
    viewModel: SelectScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val platform = LocalPlatform.current

    LaunchedEffect(platform) {
        platform.externalEvents.collect { event ->
            when (event) {
                ExternalEvent.Back -> switchScreen(Screen.Main)
            }
        }
    }

    Scaffold(
        topBar = {
            SelectScreenTopBar(
                onBackClicked = { switchScreen(Screen.Main) }
            )
        }
    ) { paddingValues ->
        SelectTextGeneratorList(
            generators = uiState.generators,
            favoriteGenerators = uiState.favoriteGenerators,
            currentGenerator = uiState.currentGenerator,
            onToggleFavorite = { viewModel.toggleFavorite(it) },
            onSelectGenerator = { generator ->
                viewModel.selectGenerator(generator)
                switchScreen(Screen.Main)
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectScreenTopBar(onBackClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(Res.string.select)) },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
private fun SelectTextGeneratorList(
    generators: List<TextGenerators>,
    favoriteGenerators: List<TextGenerators>,
    currentGenerator: TextGenerators,
    onToggleFavorite: (TextGenerators) -> Unit,
    onSelectGenerator: (TextGenerators) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteSet = remember(favoriteGenerators) {
        favoriteGenerators.toSet()
    }

    val categorizedGenerators = remember(generators, favoriteSet) {
        generators.filterNot { it in favoriteSet }.groupBy { it.category }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        if (favoriteGenerators.isNotEmpty()) {
            items(
                items = favoriteGenerators,
                key = { "favorite_${it.name}" }
            ) { generator ->
                SelectTextGeneratorItem(
                    generator = generator,
                    isSelected = generator == currentGenerator,
                    isFavorite = true,
                    onFavoriteClick = { onToggleFavorite(generator) },
                    onClick = { onSelectGenerator(generator) }
                )
            }
        }

        categorizedGenerators.forEach { (category, generatorList) ->
            item {
                Text(
                    text = category.resource?.let { resource ->
                        stringResource(resource)
                    } ?: category.name,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
            items(
                items = generatorList,
                key = { "category_${it.name}" }
            ) { generator ->
                SelectTextGeneratorItem(
                    generator = generator,
                    isSelected = generator == currentGenerator,
                    isFavorite = false,
                    onFavoriteClick = { onToggleFavorite(generator) },
                    onClick = { onSelectGenerator(generator) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SelectTextGeneratorItem(
    generator: TextGenerators,
    isSelected: Boolean,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = generator.resource?.let { resource ->
                    stringResource(resource)
                } ?: generator.name,
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = modifier
            .clip(ShapeDefaults.Medium)
            .clickable(onClick = onClick),
        trailingContent = {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Default.Star
                    } else {
                        Icons.Default.StarOutline
                    },
                    contentDescription = if (isFavorite) {
                        "Remove from favorites"
                    } else {
                        "Add to favorites"
                    }
                )
            }
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                Color.Transparent
            }
        )
    )
}
