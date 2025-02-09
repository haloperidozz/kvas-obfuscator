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

import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator

class SlavaBoguTextGenerator : SelectTextGenerator(
    "\uD83C\uDDF7\uD83C\uDDFA", "\uD83C\uDFF4\u200D☠\uFE0F",
    "\uD83C\uDDFA\uD83C\uDDE6", "\uD83C\uDFF3\uFE0F\u200D\uD83C\uDF08",
    "\uD83D\uDC96", "❤\uFE0F", "\uD83D\uDCA5", "\uD83D\uDDA4",
    "\uD83D\uDE3C"
) {
    override fun generate(input: String, selected: String, index: Int): String {
        val upperInput = input.uppercase()

        return """
            СЛАВА БОГУ $upperInput 🙏🏼❤️
            СЛАВА $upperInput 🙏🏼❤️
            АНГЕЛА ХРАНИТЕЛЯ $upperInput КАЖДОМУ ИЗ ВАС 🙏🏼❤️
            БОЖЕ ХРАНИ $upperInput 🙏🏼❤️
            СПАСИБО ВАМ НАШИ $upperInput 🙏🏼❤️$selected
            ХРАНИ $upperInput ✊$selected💯
            СПАСИБО НАШИМ МАЛЬЧИКАМ ❤️💪
        """.trimIndent().replace("\n", " ")
    }
}
