package com.github.haloperidozz.obfuscator.generator

abstract class SelectTextGenerator(
    val available: List<String>
) : TextGenerator<Int> {
    init {
        require(available.isNotEmpty()) { "available list is empty." }
    }

    constructor(vararg available: String) : this(available.toList())

    final override fun generate(input: String, value: Int): String {
        require(value in available.indices) { "value ($value) is out of bounds." }
        return generate(input, available[value], value)
    }

    protected abstract fun generate(input: String, selected: String, index: Int): String
}