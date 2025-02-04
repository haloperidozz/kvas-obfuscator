package com.github.haloperidozz.obfuscator.generator

abstract class FloatTextGenerator(
    val range: ClosedFloatingPointRange<Float> = 0.0f..1.0f
) : TextGenerator<Float> {
    override fun generate(input: String, value: Float): String {
        require(value in range) {
            "value ($value) must be between ${range.start} and ${range.endInclusive}."
        }

        return doGenerate(input, value)
    }

    abstract fun doGenerate(input: String, value: Float): String
}