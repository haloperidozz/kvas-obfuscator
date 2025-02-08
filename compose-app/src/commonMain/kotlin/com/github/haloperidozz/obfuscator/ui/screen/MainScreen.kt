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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorInfo
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorValue
import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.ui.modelview.MainScreenViewModel
import com.github.haloperidozz.obfuscator.util.LocalPlatform
import com.github.haloperidozz.obfuscator.util.PlatformType
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    switchScreen: (Screen) -> Unit,
    viewModel: MainScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextFieldLayout(
                onTextChanged = { text -> viewModel.updateText(text) },
                currentText = uiState.currentText,
                currentGeneratedText = uiState.currentGeneratedText,
                modifier = Modifier.weight(1.0f)
            )

            TextGeneratorValueEditor(
                currentGenerator = uiState.currentGenerator,
                updateGeneratorValue = {
                    value -> viewModel.updateGeneratorValue(value)
                },
                currentGeneratorValue = uiState.generatorValue,
            )

            BottomContent(
                switchScreen = switchScreen,
                currentGenerator = uiState.currentGenerator
            )
        }
    }
}

@Composable
private fun TextFieldLayout(
    onTextChanged: (String) -> Unit,
    currentText: String,
    currentGeneratedText: String,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier) {
        val isPortrait = maxWidth < maxHeight

        if (isPortrait) {
            Column(modifier = Modifier.fillMaxSize()) {
                InputTextField(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    currentText = currentText,
                    onTextChanged = onTextChanged
                )
                Spacer(modifier = Modifier.height(12.dp))
                ResultTextField(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    currentGeneratedText = currentGeneratedText
                )
            }
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                InputTextField(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    currentText = currentText,
                    onTextChanged = onTextChanged
                )
                Spacer(modifier = Modifier.width(16.dp))
                ResultTextField(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    currentGeneratedText = currentGeneratedText
                )
            }
        }
    }
}

@Composable
private fun InputTextField(
    onTextChanged: (String) -> Unit,
    currentText: String,
    modifier: Modifier = Modifier,
) {
    CustomTextField(
        value = currentText,
        onValueChange = onTextChanged,
        placeholder = { Text(text = "Enter Text Here") },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultTextField(
    currentGeneratedText: String,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val tooltipState = rememberTooltipState()
    val positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider()
    val platform = LocalPlatform.current
    val clipboard = LocalClipboardManager.current

    CustomTextField(
        value = currentGeneratedText,
        onValueChange = {},
        modifier = modifier,
        readOnly = true,
    ) {
        TooltipBox(
            positionProvider = positionProvider,
            tooltip = {
                PlainTooltip { Text(text = "Copied") }
            },
            state = tooltipState,
            focusable = false,
            enableUserInput = false
        ) {
            IconButton(
                onClick = {
                    clipboard.setText(AnnotatedString(currentGeneratedText))
                    coroutineScope.launch { tooltipState.show() }
                }
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
            }
        }

        when (platform.type) {
            PlatformType.Desktop -> {
                IconButton(onClick = {
                    platform.saveToFile(currentGeneratedText)
                }) {
                    Icon(Icons.Outlined.Save, contentDescription = "Save")
                }
            }
            PlatformType.Android -> {
                IconButton(onClick = {
                    platform.share(currentGeneratedText)
                }) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
            }
            PlatformType.Web -> { /* Nope */ }
        }
    }
}

@Composable
fun TextGeneratorValueEditor(
    currentGenerator: TextGeneratorInfo<*>,
    updateGeneratorValue: (value: TextGeneratorValue) -> Unit,
    currentGeneratorValue: TextGeneratorValue?,
    modifier: Modifier = Modifier
) {
    when (currentGeneratorValue) {
        is TextGeneratorValue.FloatValue -> {
            if (currentGenerator.instance !is FloatTextGenerator) {
                return
            }

            Slider(
                value = currentGeneratorValue.value,
                valueRange = currentGenerator.instance.range,
                onValueChange = { value ->
                    updateGeneratorValue(TextGeneratorValue.FloatValue(value))
                },
                modifier = modifier
            )
        }

        is TextGeneratorValue.StringValue -> {
            CustomTextField(
                value = currentGeneratorValue.value,
                onValueChange = { value ->
                    updateGeneratorValue(TextGeneratorValue.StringValue(value))
                },
                placeholder = { Text(text = "Special Text Here") },
                singleLine = true,
                modifier = modifier.fillMaxWidth()
            )
        }

        is TextGeneratorValue.SelectValue -> {
            SelectValueComposable(
                currentGenerator = currentGenerator,
                updateGeneratorValue = updateGeneratorValue,
                currentGeneratorValue = currentGeneratorValue,
                modifier = modifier
            )
        }

        else -> { /* Nope */ }
    }
}

@Composable
private fun SelectValueComposable(
    currentGenerator: TextGeneratorInfo<*>,
    updateGeneratorValue: (value: TextGeneratorValue) -> Unit,
    currentGeneratorValue: TextGeneratorValue.SelectValue,
    modifier: Modifier,
) {
    if (currentGenerator.instance !is SelectTextGenerator) {
        return
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(currentGenerator.instance.available) { index, option ->
            FilterChip(
                onClick = {
                    updateGeneratorValue(TextGeneratorValue.SelectValue(index))
                },
                label = { Text(option) },
                selected = currentGeneratorValue.index == index,
                leadingIcon = if (currentGeneratorValue.index == index) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Selected",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                border = null,
                elevation = null
            )
        }
    }
}

@Composable
private fun BottomContent(
    switchScreen: (Screen) -> Unit,
    currentGenerator: TextGeneratorInfo<*>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(60.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { switchScreen(Screen.Select) },
            modifier = Modifier
                .weight(1.0f)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            shape = MaterialTheme.shapes.large,
            elevation = null
        ) {
            Text(text = currentGenerator.id)
        }

        Button(
            onClick = { switchScreen(Screen.Info) },
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1.0f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            shape = MaterialTheme.shapes.large,
            elevation = null,
            contentPadding = PaddingValues(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info"
            )
        }
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    endContent: @Composable (() -> Unit)? = null,
) {
    val textFieldColors = if (readOnly) {
        OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        )
    } else {
        OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        )
    }

    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight,
            )
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = textFieldColors,
            placeholder = placeholder,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.matchParentSize(),
            singleLine = singleLine,
            readOnly = readOnly,
        )

        endContent?.let { content ->
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 8.dp, bottom = 8.dp)
            ) {
                content()
            }
        }
    }
}
