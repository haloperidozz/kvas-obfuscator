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
package com.github.haloperidozz.obfuscator.generator.impl

import com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator

class TitloTextGenerator : StringTextGenerator {
    override fun generate(input: String, value: String): String {
        return input.mapIndexed { index, char ->
            char + (DIACRITICAL_MAP[value.lowercase().getOrNull(index)] ?: "")
        }.joinToString("")
    }

    companion object {
        private val DIACRITICAL_MAP = mapOf(
            'а' to "\u2DF6", 'б' to "\u2DE0", 'в' to "\u2DE1", 'г' to "\u2DE2",
            'д' to "\u2DE3", 'е' to "\u2DF7", 'ё' to "\u2DF7", 'ж' to "\u2DE4",
            'з' to "\u2DE5", 'и' to "\uA675", 'й' to "\uA675", 'к' to "\u2DE6",
            'л' to "\u2DE7", 'м' to "\u2DE8", 'н' to "\u2DE9", 'о' to "\u2DEA",
            'п' to "\u2DEB", 'р' to "\u2DEC", 'с' to "\u2DED", 'т' to "\u2DEE",
            'у' to "\uA677", 'х' to "\u2DEF", 'ц' to "\u2DF0", 'ч' to "\u2DF1",
            'ш' to "\u2DF2", 'щ' to "\u2DF3", 'ъ' to "\uA678", 'ы' to "\uA679",
            'ь' to "\uA67A", 'ю' to "\u2DFB",
        )
    }
}
