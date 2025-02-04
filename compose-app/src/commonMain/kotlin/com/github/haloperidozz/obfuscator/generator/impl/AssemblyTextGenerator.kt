package com.github.haloperidozz.obfuscator.generator.impl

import com.github.haloperidozz.obfuscator.generator.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.util.ISO9

class AssemblyTextGenerator : SimpleTextGenerator() {
    override fun generate(input: String): String {
        val bytes = ISO9.Simple.transliterate(input).encodeToByteArray()

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