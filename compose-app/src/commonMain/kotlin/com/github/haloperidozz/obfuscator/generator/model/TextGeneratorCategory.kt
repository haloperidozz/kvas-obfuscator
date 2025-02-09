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
package com.github.haloperidozz.obfuscator.generator.model

import kvas_obfuscator.compose_app.generated.resources.*
import kvas_obfuscator.compose_app.generated.resources.Res
import kvas_obfuscator.compose_app.generated.resources.category_cipher
import kvas_obfuscator.compose_app.generated.resources.category_programming
import kvas_obfuscator.compose_app.generated.resources.category_shitposter
import org.jetbrains.compose.resources.StringResource

enum class TextGeneratorCategory(val resource: StringResource? = null) {
    Shitposter(resource = Res.string.category_shitposter),
    Cipher(resource = Res.string.category_cipher),
    Programming(resource = Res.string.category_programming),
    Typography(resource = Res.string.category_typography),
    Script(resource = Res.string.category_script),
    Converter(resource = Res.string.category_converter),
    Other(resource = Res.string.category_other),
    Unknown(resource = Res.string.category_unknown),
}
