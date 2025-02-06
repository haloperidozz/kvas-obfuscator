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

import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import kotlin.math.abs
import kotlin.math.sqrt

class BrainfuckTextGenerator : SimpleTextGenerator() {
    override fun generate(input: String): String = buildString {
        var lastCharCode = 0

        input.forEach { char ->
            val diff = char.code - lastCharCode

            if (diff != 0) {
                append(optimizedAdjustment(diff))
            }

            append(".")
            lastCharCode = char.code
        }
    }

    private fun optimizedAdjustment(diff: Int): String {
        val absDiff = abs(diff)
        val op = if (diff > 0) "+" else "-"

        if (absDiff < 10) return op.repeat(absDiff)

        val components = factorComponents(absDiff) ?: return op.repeat(absDiff)

        val (factor, multiplier, remainder) = components

        return buildString {
            append(">")                       // Move to cell1
            append("+".repeat(factor))        // Initialize helper cell to factor
            append("[")                       // Start loop
            append("<")                       // Go back to cell0
            append(op.repeat(multiplier))     // Adjust cell0 by multiplier
            append(">")                       // Return to cell1
            append("-")                       // Decrement helper cell
            append("]")                       // End loop
            append("<")                       // Return to cell0

            if (remainder > 0) {
                append(op.repeat(remainder))  // Leftover adjustment
            }
        }
    }

    private fun factorComponents(absDiff: Int): Triple<Int, Int, Int>? {
        val sqrtTarget = sqrt(absDiff.toDouble()).toInt()

        for (a in sqrtTarget downTo 2) {
            val b = absDiff / a
            val r = absDiff - a * b

            if (a * b + r == absDiff) return Triple(a, b, r)
        }

        return null
    }
}
