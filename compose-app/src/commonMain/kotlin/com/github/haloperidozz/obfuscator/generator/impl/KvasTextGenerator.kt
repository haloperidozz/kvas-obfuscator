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

import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.util.transformEach
import kotlin.random.Random

class KvasTextGenerator : FloatTextGenerator() {
    override fun doGenerate(input: String, value: Float): String {
        return input.transformEach { char ->
            if (Random.nextFloat() > value) {
                char
            } else {
                KVAS_KEYBOARD_MAPPING[char]?.random() ?: char
            }
        }
    }

    companion object {
        private val KVAS_KEYBOARD_MAPPING = mapOf(
            'а' to listOf("в", "п"),
            'б' to listOf("ь", "ю"),
            'в' to listOf("ы", "а"),
            'г' to listOf("н", "ш"),
            'д' to listOf("л", "ж"),
            'е' to listOf("к", "н"),
            'ж' to listOf("д", "э"),
            'з' to listOf("щ", "х"),
            'и' to listOf("м", "т"),
            'й' to listOf("ц"),
            'к' to listOf("у", "е"),
            'л' to listOf("о", "д"),
            'м' to listOf("с", "и"),
            'н' to listOf("е", "г"),
            'о' to listOf("р", "л"),
            'п' to listOf("а", "р"),
            'р' to listOf("п", "о"),
            'с' to listOf("ч", "м"),
            'т' to listOf("и", "ь"),
            'у' to listOf("ц", "к"),
            'ф' to listOf("ы"),
            'х' to listOf("з", "ъ"),
            'ц' to listOf("й", "у"),
            'ч' to listOf("я", "с"),
            'ш' to listOf("г", "щ"),
            'щ' to listOf("ш", "з"),
            'ъ' to listOf("х"),
            'ы' to listOf("ф", "в"),
            'ь' to listOf("т", "б"),
            'э' to listOf("ж"),
            'ю' to listOf("б"),
            'я' to listOf("ч"),
            'a' to listOf("s"),
            'b' to listOf("v", "n"),
            'c' to listOf("x", "v"),
            'd' to listOf("s", "f"),
            'e' to listOf("w", "r"),
            'f' to listOf("d", "g"),
            'g' to listOf("f", "h"),
            'h' to listOf("g", "j"),
            'i' to listOf("u", "o"),
            'j' to listOf("h", "k"),
            'k' to listOf("j", "l"),
            'l' to listOf("k"),
            'm' to listOf("n"),
            'n' to listOf("b", "m"),
            'o' to listOf("i", "p"),
            'p' to listOf("o"),
            'q' to listOf("w"),
            'r' to listOf("e", "t"),
            's' to listOf("a", "d"),
            't' to listOf("r", "y"),
            'u' to listOf("y", "i"),
            'v' to listOf("c", "b"),
            'w' to listOf("q", "e"),
            'x' to listOf("z", "c"),
            'y' to listOf("t", "u"),
            'z' to listOf("x")
        ).flatMap { (key, value) ->
            listOf(
                key.lowercaseChar() to value.map { it.lowercase() },
                key.uppercaseChar() to value.map { it.uppercase() }
            )
        }.toMap()
    }
}
