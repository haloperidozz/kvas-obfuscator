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
package com.github.haloperidozz.obfuscator.generator

/**
 * WARNING: Do **NOT** implement this interface directly.
 *
 * Instead, use one of the provided implementations:
 * - [com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator]
 * - [com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator]
 * - [com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator]
 * - [com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator]
 */
interface TextGenerator<T> {
    fun generate(input: String, value: T): String
}
