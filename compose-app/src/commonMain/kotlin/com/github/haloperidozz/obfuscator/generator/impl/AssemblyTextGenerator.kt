package com.github.haloperidozz.obfuscator.generator.impl

import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.generator.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.generator.TextGeneratorMeta

class AssemblyTextGenerator : SimpleTextGenerator() {
    override val meta: TextGeneratorMeta = TextGeneratorMeta(
        id = "assembly",
        category = TextGeneratorCategory.Programming
    )

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