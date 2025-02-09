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
package com.github.haloperidozz.obfuscator.util

object Alphabet {
    const val ENGLISH_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz"

    const val RUSSIAN_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    const val RUSSIAN_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"

    const val ENGLISH_VOWELS = "AEIOUYaeiouy"
    const val RUSSIAN_VOWELS = "АЕЁИОУЫЭЮЯаеёиоуыэюя"
    const val ALL_VOWELS = ENGLISH_VOWELS + RUSSIAN_VOWELS

    const val RUSSIAN_CONSONANTS = "БВГДЖЗЙКЛМНПРСТФХЦЧШЩбвгджзйклмнпрстфхцчшщ"
    const val ENGLISH_CONSONANTS = "BCDFGHJKLMNPQRSTVWXZbcdfghjklmnpqrstvwxz"
    const val ALL_CONSONANTS = ENGLISH_CONSONANTS + RUSSIAN_CONSONANTS

    const val RUSSIAN_SOFT_AND_HARD_SIGNS = "ЬьЪъ"
}
