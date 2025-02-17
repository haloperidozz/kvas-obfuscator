package com.github.haloperidozz.obfuscator.generator.impl

import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.util.transformEach

class CombiningTextGenerator : SelectTextGenerator(
    Combining.entries.map { it.title }
) {
    override fun generate(input: String, selected: String, index: Int): String {
        return input.transformEach { char ->
            "$char${Combining.entries[index].diacritical}"
        }
    }

    private enum class Combining(val title: String, val diacritical: String) {
        ENCLOSING_CIRCLE_BACKSLASH("N⃠O⃠", "⃠"),
        LONG_SOLIDUS_OVERLAY("S̸l̸a̸s̸h̸", "̸"),
        ENCLOSING_KEYCAP("K⃣e⃣y⃣c⃣a⃣p⃣", "⃣"),
        CYRILLIC_MILLIONS_SIGN("M҉I҉L҉L҉I҉O҉N҉S҉", "҉"),
        LONG_STROKE_OVERLAY("s̶t̶r̶i̶k̶e̶t̶h̶r̶o̶u̶g̶h̶", "̶"),
        TILDE_OVERLAY("t̴i̴l̴d̴e̴", "̴"),
        SHORT_SOLIDUS_OVERLAY("s̷h̷o̷r̷t̷ ̷s̷l̷a̷s̷h̷ ̷o̷v̷e̷r̷l̷a̷y̷", "̷"),
        LOW_LINE("u̲n̲d̲e̲r̲l̲i̲n̲e̲", "̲"),
        DOUBLE_LOW_LINE("d̳o̳u̳b̳l̳e̳ ̳u̳n̳d̳e̳r̳l̳i̳n̳e̳", "̳"),
        VERTICAL_TILDE("y̾e̾r̾i̾k̾", "̾"),
        UPWARDS_ARROW_BELOW("a͎r͎r͎o͎w͎", "͎"),
        X_ABOVE_X_BELOW("x͓̽a͓̽b͓̽o͓̽v͓̽e͓̽x͓̽b͓̽e͓̽l͓̽o͓̽w͓̽", "͓̽"),
    }
}