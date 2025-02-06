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

class AssemblyTextGenerator : SimpleTextGenerator() {
    override fun generate(input: String): String {
        val bytes = input.encodeToByteArray()

        return buildString {
            appendLine("section .bss")
            appendLine("    buffer resb ${bytes.size}")
            appendLine()
            appendLine("section .text")
            appendLine("    global _start")
            appendLine()
            appendLine("_start:")

            for ((index, byte) in bytes.withIndex()) {
                val byteString = byte.toUByte().toString(16)
                appendLine("    mov byte [buffer + $index], 0x$byteString")
            }

            appendLine()
            appendLine("    mov rax, 1")
            appendLine("    mov rdi, 1")
            appendLine("    lea rsi, [buffer]")
            appendLine("    mov rdx, ${bytes.size}")
            appendLine("    syscall")
            appendLine()
            appendLine("    mov rax, 60")
            appendLine("    xor rdi, rdi")
            appendLine("    syscall")
        }
    }
}
