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
package com.github.haloperidozz.obfuscator.generator

import com.github.haloperidozz.obfuscator.generator.impl.*
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorCategory
import com.github.haloperidozz.obfuscator.generator.model.TextGeneratorValue
import com.github.haloperidozz.obfuscator.generator.type.FloatTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SelectTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.SimpleTextGenerator
import com.github.haloperidozz.obfuscator.generator.type.StringTextGenerator
import com.github.haloperidozz.obfuscator.util.Blowfish
import com.github.haloperidozz.obfuscator.util.ISO9
import kvas_obfuscator.compose_app.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import kotlin.io.encoding.ExperimentalEncodingApi

enum class TextGenerators(
    val category: TextGeneratorCategory = TextGeneratorCategory.Unknown,
    val resource: StringResource? = null,
    val instance: TextGenerator<*>
) {
    Kvas(
        category = TextGeneratorCategory.Shitposter,
        resource = Res.string.text_generator_kvas,
        instance = KvasTextGenerator()
    ),
    CaesarCipher(
        category = TextGeneratorCategory.Cipher,
        resource = Res.string.text_generator_caesar,
        instance = CaesarCipherTextGenerator()
    ),
    AtbashCipher(
        category = TextGeneratorCategory.Cipher,
        resource = Res.string.text_generator_atbash,
        instance = AtbashCipherTextGenerator()
    ),
    Titlo(
        category = TextGeneratorCategory.Other,
        resource = Res.string.text_generator_titlo,
        instance = TitloTextGenerator()
    ),
    Zalgo(
        category = TextGeneratorCategory.Other,
        resource = Res.string.text_generator_zargo,
        instance = ZalgoTextGenerator()
    ),
    Numeral(
        category = TextGeneratorCategory.Converter,
        resource = Res.string.text_generator_numeral,
        instance = NumeralTextGenerator()
    ),
    Tortik(
        category = TextGeneratorCategory.Shitposter,
        resource = Res.string.text_generator_tortik,
        instance = HtmlEntitiesTextGenerator()
    ),
    SlavaBogu(
        category = TextGeneratorCategory.Other,
        resource = Res.string.text_generator_pasta,
        instance = SlavaBoguTextGenerator()
    ),
    Case(
        category = TextGeneratorCategory.Typography,
        resource = Res.string.text_generator_case,
        instance = CaseTextGenerator()
    ),
    Pechenka(
        category = TextGeneratorCategory.Shitposter,
        resource = Res.string.text_generator_pechenka,
        instance = PechenkaTextGenerator()
    ),
    Assembly(
        category = TextGeneratorCategory.Programming,
        resource = Res.string.text_generator_assembly,
        instance = AssemblyTextGenerator().modify { generator, input ->
            generator.generate(ISO9.Simple.transliterate(input), Unit)
        }
    ),
    Brainfuck(
        category = TextGeneratorCategory.Programming,
        resource = Res.string.text_generator_brainfuck,
        instance = BrainfuckTextGenerator().modify { generator, input ->
            generator.generate(ISO9.Simple.transliterate(input), Unit)
        }
    ),
    Bbubble(
        category = TextGeneratorCategory.Shitposter,
        resource = Res.string.text_generator_bbubble,
        instance = LetterReplaceTextGenerator("☆", "★", "✫"),
    ),
    Kitsu(
        category = TextGeneratorCategory.Shitposter,
        resource = Res.string.text_generator_kitsu,
        instance = LetterReplaceTextGenerator("🤍", "🖤", "🩶"),
    ),
    Translit(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_translit,
        instance = object : SimpleTextGenerator() {
            override fun generate(input: String): String {
                return ISO9.Standard.transliterate(input)
            }
        }
    ),
    Reversed(
        category = TextGeneratorCategory.Other,
        resource = Res.string.text_generator_reversed,
        instance = object : SimpleTextGenerator() {
            override fun generate(input: String): String = input.reversed()
        }
    ),
    @OptIn(ExperimentalEncodingApi::class)
    Base64(
        category = TextGeneratorCategory.Converter,
        resource = Res.string.text_generator_base64,
        instance = object : SimpleTextGenerator() {
            override fun generate(input: String): String {
                return kotlin.io.encoding.Base64.encode(input.encodeToByteArray())
            }
        }
    ),
    Venom(
        category = TextGeneratorCategory.Other,
        resource = Res.string.text_generator_venom,
        instance = object : SimpleTextGenerator() {
            override fun generate(input: String): String {
                if (input.isBlank()) return ""

                return input.split(" ").joinToString(" ") { word ->
                    if (word.length <= 2) "venom" else "ven" + word.drop(word.length / 2)
                }
            }
        }
    ),
    HexArray(
        category = TextGeneratorCategory.Other,
        resource = Res.string.text_generator_hex_array,
        instance = object : SimpleTextGenerator() {
            override fun generate(input: String): String = input.encodeToByteArray()
                .map { "'\\x${it.toUByte().toString(16).padStart(2, '0')}'" }
                .plus("'\\0'")
                .joinToString(", ", "{", "}")
        }
    ),
    @OptIn(ExperimentalStdlibApi::class)
    BlowfishEncrypt(
        category = TextGeneratorCategory.Cipher,
        resource = Res.string.text_generator_blowfish,
        instance = object : StringTextGenerator {
            override fun generate(input: String, value: String): String {
                if (input.isBlank() || value.isBlank()) return ""

                val key = value.encodeToByteArray()
                val data = input.encodeToByteArray()

                return Blowfish(key).encryptData(data).toHexString()
            }
        }
    ),
    Wingdings(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_wingdings,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "а" to "🡪", "б" to "🡩", "в" to "🡫", "г" to "🡬", "д" to "🡭",
                "е" to "🡯", "ё" to "🕑", "ж" to "🡮", "з" to "🡸", "и" to "🡺",
                "й" to "🡹", "к" to "🡻", "л" to "🡼", "м" to "🡽", "н" to "🡿",
                "о" to "🡾", "п" to "⇦", "р" to "⇨", "с" to "⇧", "т" to "⇩",
                "у" to "⬄", "ф" to "⇳", "х" to "⬁", "ц" to "⬀", "ч" to "⬃",
                "ш" to "⬂", "щ" to "🢬", "ъ" to "🢭", "ы" to "🗶", "ь" to "✔",
                "э" to "🗷", "ю" to "🗹", "я" to "🪟",

                "А" to "🕙", "Б" to "🕚", "В" to "🕛", "Г" to "⮰", "Д" to "⮱",
                "Е" to "⮲", "Ё" to "◻", "Ж" to "⮳", "З" to "⮴", "И" to "⮵",
                "Й" to "⮶", "К" to "⮷", "Л" to "🙪", "М" to "🙫", "Н" to "🙕",
                "О" to "🙔", "П" to "🙗", "Р" to "🙖", "С" to "🙐", "Т" to "🙑",
                "У" to "🙒", "Ф" to "🕓", "Х" to "⌫", "Ц" to "⌦", "Ч" to "⮘",
                "Ш" to "⮚", "Щ" to "⮙", "Ъ" to "⮛", "Ы" to "⮈", "Ь" to "⮊",
                "Э" to "⮉", "Ю" to "⮋", "Я" to "🡨",

                "a" to "♋", "b" to "♌", "c" to "♍", "d" to "♎", "e" to "♏",
                "f" to "♐", "g" to "♑", "h" to "♒", "i" to "♓", "j" to "︎🙰",
                "k" to "🙵", "l" to "●", "m" to "❍", "n" to "■", "o" to "□",
                "p" to "◻", "q" to "❑", "r" to "❒", "s" to "⬧", "t" to "⧫",
                "u" to "◆", "v" to "❖", "w" to "⬥", "x" to "⌧", "y" to "⍓",
                "z" to "⌘",

                "A" to "✌", "B" to "👌", "C" to "👍", "D" to "👎", "E" to "☜",
                "F" to "☞", "G" to "☝", "H" to "☟", "I" to "✋", "J" to "☺",
                "K" to "😐", "L" to "☹", "M" to "💣", "N" to "☠", "O" to "⚐",
                "P" to "🏱︎", "Q" to "✈", "R" to "☼", "S" to "💧︎", "T" to "❄",
                "U" to "🕆︎", "V" to "✞", "W" to "🕈︎", "X" to "✠", "Y" to "✡",
                "Z" to "☪",

                "0" to "📁", "1" to "📂", "2" to "📄", "3" to "🗏", "4" to "🗐",
                "5" to "🗄", "6" to "⌛", "7" to "🖮", "8" to "🖰", "9" to "🖲",

                "." to "📬", "," to "📪", "?" to "✍", "'" to "ॐ", "!" to "🖉",
                "/" to "📭", "(" to "🕿", ")" to "✆", "&" to "🕮", ":" to "🖳",
                ";" to "🖴", "=" to "🖬", "+" to "🖃", "-" to "📫", "_" to "♉",
                "\"" to "✂", "$" to "👓", "@" to "🖎"
            )
        )
    ),
    SGA(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_sga,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "a" to "ᖋ", "b" to "ᕊ", "c" to "าํ", "d" to "੮", "e" to "ᒷ",
                "f" to "𝌂", "g" to "⫞", "h" to "⫧", "i" to "¦", "j" to "⁝",
                "k" to "ꖌ", "l" to "|∶", "m" to "⟓", "n" to "ㇼ", "o" to "੭",
                "p" to "!¡", "q" to "ᑑ", "r" to "∷", "s" to "\uD840\uDCD1", "t" to "ㄱ",
                "u" to "⸚", "v" to "⫨", "w" to "∴", "x" to "ꜘ", "y" to "∥",
                "z" to "Ո", "." to "._."
            ),
            caseSensitive = false
        ).modify { generator, input ->
            val translit = ISO9.Simple.transliterate(input)

            generator.generate(translit.filter { char ->
                char.isLetter() || char.isWhitespace() || char == '.'
            }, Unit)
        }
    ),
    Glagolitic(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_glagolitic,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "а" to "ⰰ", "б" to "ⰱ", "в" to "ⰲ", "г" to "ⰳ", "д" to "ⰴ",
                "е" to "ⰵ", "ё" to "ⱖ", "ж" to "ⰶ", "з" to "ⰸ", "и" to "ⰻ",
                "й" to "ⰼ", "к" to "ⰽ", "л" to "ⰾ", "м" to "ⰿ", "н" to "ⱀ",
                "о" to "ⱁ", "п" to "ⱂ", "р" to "ⱃ", "с" to "ⱄ", "т" to "ⱅ",
                "у" to "ⱆ", "ф" to "ⱇ", "х" to "ⱈ", "ц" to "ⱌ", "ч" to "ⱍ",
                "ш" to "ⱎ", "щ" to "ⱋ", "ъ" to "ⱏ", "ы" to "ⰺ", "ь" to "ⱐ",
                "э" to "ⱗ", "ю" to "ⱓ", "я" to "ⱔ", "А" to "Ⰰ", "Б" to "Ⰱ",
                "В" to "Ⰲ", "Г" to "Ⰳ", "Д" to "Ⰴ", "Е" to "Ⰵ", "Ё" to "Ⱖ",
                "Ж" to "Ⰶ", "З" to "Ⰸ", "И" to "Ⰻ", "Й" to "Ⰼ", "К" to "Ⰽ",
                "Л" to "Ⰾ", "М" to "Ⰿ", "Н" to "Ⱀ", "О" to "Ⱁ", "П" to "Ⱂ",
                "Р" to "Ⱃ", "С" to "Ⱄ", "Т" to "Ⱅ", "У" to "Ⱆ", "Ф" to "Ⱇ",
                "Х" to "Ⱈ", "Ц" to "Ⱌ", "Ч" to "Ⱍ", "Ш" to "Ⱎ", "Щ" to "Ⱋ",
                "Ъ" to "Ⱏ", "Ы" to "Ⰺ", "Ь" to "Ⱐ", "Э" to "Ⱗ", "Ю" to "Ⱓ",
                "Я" to "Ⱔ"
            )
        )
    ),
    Phoenician(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_phoenician,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "А" to "𐤀", "Б" to "𐤁", "В" to "𐤅", "Г" to "𐤂", "Д" to "𐤃",
                "Е" to "", "Ё" to "", "Ж" to "𐤆𐤔", "З" to "𐤆", "И" to "𐤉",
                "Й" to "𐤉", "К" to "𐤊", "Л" to "𐤋", "М" to "𐤌", "Н" to "𐤍",
                "О" to "", "П" to "𐤐", "Р" to "𐤓", "С" to "𐤎", "Т" to "𐤕",
                "У" to "", "Ф" to "𐤐𐤄", "Х" to "𐤇", "Ц" to "𐤈𐤎", "Ч" to "𐤈𐤔",
                "Ш" to "𐤔𐤔", "Щ" to "𐤔𐤔𐤉", "Ъ" to "", "Ы" to "𐤉𐤅", "Ь" to "",
                "Э" to "𐤏", "Ю" to "𐤉𐤅𐤏", "Я" to "𐤉𐤏",

                "A" to "𐤀", "B" to "𐤁", "C" to "𐤎", "D" to "𐤃", "E" to "𐤄",
                "F" to "𐤐𐤄", "G" to "𐤂", "H" to "𐤇", "I" to "𐤉", "J" to "𐤉𐤄",
                "K" to "𐤊", "L" to "𐤋", "M" to "𐤌", "N" to "𐤍", "O" to "𐤏",
                "P" to "𐤐", "Q" to "𐤒", "R" to "𐤓", "S" to "𐤎", "T" to "𐤕",
                "U" to "𐤅𐤏", "V" to "𐤅", "W" to "𐤅𐤅", "X" to "𐤎𐤔", "Y" to "𐤉",
                "Z" to "𐤆"
            ),
            caseSensitive = false
        )
    ),
    Rune(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_rune,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "А" to "ᚨ", "Б" to "ᛒ", "В" to "ᚹ", "Г" to "ᚷ", "Д" to "ᛞ",
                "Е" to "ᛖ", "Ё" to "ᛖ", "Ж" to "ᛇᛋ", "З" to "ᛉ", "И" to "ᛁ",
                "Й" to "ᛁ", "К" to "ᚲ", "Л" to "ᛚ", "М" to "ᛗ", "Н" to "ᚾ",
                "О" to "ᛟ", "П" to "ᛈ", "Р" to "ᚱ", "С" to "ᛊ", "Т" to "ᛏ",
                "У" to "ᚢ", "Ф" to "ᚠ", "Х" to "ᚺ", "Ц" to "ᚲᛊ", "Ч" to "ᚲᚷ",
                "Ш" to "ᛊᛊ", "Щ" to "ᛊᛊᚲ", "Ъ" to "ᛟ", "Ы" to "ᛁᚢ", "Ь" to "ᛟ",
                "Э" to "ᛖ", "Ю" to "ᚢᛟ", "Я" to "ᛁᛟ",

                "A" to "ᚨ", "B" to "ᛒ", "C" to "ᚲ", "D" to "ᛞ", "E" to "ᛖ",
                "F" to "ᚠ", "G" to "ᚷ", "H" to "ᚺ", "I" to "ᛁ", "J" to "ᛃ",
                "K" to "ᚲ", "L" to "ᛚ", "M" to "ᛗ", "N" to "ᚾ", "O" to "ᛟ",
                "P" to "ᛈ", "Q" to "ᚲᚢ", "R" to "ᚱ", "S" to "ᛊ", "T" to "ᛏ",
                "U" to "ᚢ", "V" to "ᚹ", "W" to "ᚹ", "X" to "ᛊᚲ", "Y" to "ᛁ",
                "Z" to "ᛉ"
            ),
            caseSensitive = false
        )
    ),
    Leet(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_leet,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "a" to "4", "а" to "4",
                "b" to "8", "б" to "6",
                "c" to "<", "в" to "8",
                "d" to "[)", "г" to "|`",
                "e" to "3", "д" to "|)",
                "f" to "|", "е" to "3",
                "g" to "6", "ё" to "3",
                "h" to "#", "ж" to "}|{",
                "i" to "1", "з" to "3",
                "j" to "_|", "и" to "|/|",
                "k" to "|<", "й" to "|/|",
                "l" to "1", "к" to "|<",
                "m" to "[V]", "л" to "/\\",
                "n" to "^/", "м" to "/\\/\\",
                "o" to "0", "н" to "|-|",
                "p" to "|*", "о" to "0",
                "q" to "0_", "п" to "||",
                "r" to "I2", "р" to "|*",
                "s" to "5", "с" to "<",
                "t" to "7", "т" to "7",
                "u" to "(_)", "у" to "'/",
                "v" to "\\/", "ф" to "qp",
                "w" to "\\/\\/", "х" to "><",
                "x" to "><", "ц" to "|_|,",
                "y" to "`/", "ч" to "'-|",
                "z" to "2", "ш" to "III",
                "щ" to "l_l_l,",
                "ъ" to "'b",
                "ы" to "b|",
                "ь" to "b",
                "э" to "€",
                "ю" to "|-0",
                "я" to "9I"
            ),
            caseSensitive = false
        )
    ),
    MorseCode(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_morsecode,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "a" to ".-", "а" to ".-",
                "b" to "-...", "б" to "-...",
                "c" to "-.-.", "в" to ".--",
                "d" to "-..", "г" to "--.",
                "e" to ".", "д" to "-..",
                "f" to "..-.", "е" to ".",
                "g" to "--.", "ё" to ".",
                "h" to "....", "ж" to "...-",
                "i" to "..", "з" to "--..",
                "j" to ".---", "и" to "..",
                "k" to "-.-", "й" to ".---",
                "l" to ".-..", "к" to "-.-",
                "m" to "--", "л" to ".-..",
                "n" to "-.", "м" to "--",
                "o" to "---", "н" to "-.",
                "p" to ".--.", "о" to "---",
                "q" to "--.-", "п" to ".--.",
                "r" to ".-.", "р" to ".-.",
                "s" to "...", "с" to "...",
                "t" to "-", "т" to "-",
                "u" to "..-", "у" to "..-",
                "v" to "...-", "ф" to "..-.",
                "w" to ".--", "х" to "....",
                "x" to "-..-", "ц" to "-.-.",
                "y" to "-.--", "ч" to "---.",
                "z" to "--..", "ш" to "----",
                "щ" to "--.-",
                "ъ" to ".--.-.",
                "ы" to "-.--",
                "ь" to "-..-",
                "э" to "..-..",
                "ю" to "..--",
                "я" to ".-.-",

                "0" to "-----", "1" to ".----",
                "2" to "..---", "3" to "...--",
                "4" to "....-", "5" to ".....",
                "6" to "-....", "7" to "--...",
                "8" to "---..", "9" to "----.",

                "." to ".-.-.-", "," to "--..--",
                "?" to "..--..", "'" to ".----.",
                "!" to "-.-.--", "/" to "-..-.",
                "(" to "-.--.", ")" to "-.--.-",
                "&" to ".-...", ":" to "---...",
                ";" to "-.-.-.", "=" to "-...-",
                "+" to ".-.-.", "-" to "-....-",
                "_" to "..--.-", "\"" to ".-..-.",
                "$" to "...-..-", "@" to ".--.-."
            ),
            caseSensitive = false,
            delimiter = " "
        ).modify { generator, input ->
            if (input.isBlank()) return@modify input

            input.trim().split("\\s+".toRegex()).joinToString(" / ") { word ->
                generator.generate(word, Unit)
            }
        }
    ),
    Lamushix(
        category = TextGeneratorCategory.Shitposter,
        resource = Res.string.text_generator_lamushix,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "а" to "_", "б" to "п", "в" to "ф", "г" to "к", "д" to "т",
                "е" to "5", "ё" to "5", "ж" to "ш", "з" to "с", "и" to ":",
                "й" to "й", "к" to "г", "л" to "л", "м" to "м", "н" to "н",
                "о" to "+", "п" to "б", "р" to "р", "с" to "з", "т" to "д",
                "у" to "3", "ф" to "в", "х" to "х", "ц" to "ц", "ч" to "ч",
                "ш" to "ж", "щ" to "щ", "ъ" to "ъ", "ы" to "#", "ь" to "ь",
                "э" to "/", "ю" to "?", "я" to "*"
            ),
            caseSensitive = false
        )
    ),
    Braille(
        category = TextGeneratorCategory.Script,
        resource = Res.string.text_generator_braille,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "а" to "⠁", "б" to "⠃", "в" to "⠺", "г" to "⠛", "д" to "⠙",
                "е" to "⠑", "ё" to "⠡", "ж" to "⠚", "з" to "⠵", "и" to "⠊",
                "й" to "⠯", "к" to "⠅", "л" to "⠇", "м" to "⠍", "н" to "⠝",
                "о" to "⠕", "п" to "⠏", "р" to "⠗", "с" to "⠎", "т" to "⠞",
                "у" to "⠥", "ф" to "⠋", "х" to "⠓", "ц" to "⠉", "ч" to "⠟",
                "ш" to "⠱", "щ" to "⠭", "ъ" to "⠷", "ы" to "⠮", "ь" to "⠾",
                "э" to "⠪", "ю" to "⠳", "я" to "⠫",

                "a" to "⠁", "b" to "⠃", "c" to "⠉", "d" to "⠙", "e" to "⠑",
                "f" to "⠋", "g" to "⠛", "h" to "⠓", "i" to "⠊", "j" to "⠚",
                "k" to "⠅", "l" to "⠇", "m" to "⠍", "n" to "⠝", "o" to "⠕",
                "p" to "⠏", "q" to "⠟", "r" to "⠗", "s" to "⠎", "t" to "⠞",
                "u" to "⠥", "v" to "⠧", "w" to "⠺", "x" to "⠭", "y" to "⠽",
                "z" to "⠵",

                "0" to "⠼⠚", "1" to "⠼⠁", "2" to "⠼⠃", "3" to "⠼⠉", "4" to "⠼⠙",
                "5" to "⠼⠑", "6" to "⠼⠋", "7" to "⠼⠛", "8" to "⠼⠓", "9" to "⠼⠊",

                "." to "⠲", "," to "⠂", "?" to "⠢", "'" to "⠄", "!" to "⠖",
                "/" to "⠌", "(" to "⠣", ")" to "⠜", ":" to "⠒", ";" to "⠰",
                "=" to "⠿", "+" to "⠬", "-" to "⠤", "_" to "⠸", "\"" to "⠐",
                "@" to "⠩"
            ),
            caseSensitive = false
        )
    ),
    Alien(
        category = TextGeneratorCategory.Other,
        resource = Res.string.text_generator_locked_in_alien,
        instance = TextReplacerTextGenerator(
            replacements = mapOf(
                "a" to "⏃", "b" to "⏚", "c" to "☊", "d" to "⎅", "e" to "⟒",
                "f" to "⎎", "g" to "☌", "h" to "⊑", "i" to "⟟", "j" to "⟊",
                "k" to "☍", "l" to "⌰", "m" to "⋔", "n" to "⋏", "o" to "⍜",
                "p" to "⌿", "q" to "⍾", "r" to "⍀", "s" to "⌇", "t" to "⏁",
                "u" to "⎍", "v" to "⎐", "w" to "⍙", "x" to "⌖", "y" to "⊬",
                "z" to "⋉"
            ),
            caseSensitive = false
        ).modify { generator, input ->
            generator.generate(ISO9.Simple.transliterate(input).filter {
                it.isLetter() || it.isWhitespace()
            }, Unit)
        }
    );

    fun defaultValue(): TextGeneratorValue? {
        return when (instance) {
            is FloatTextGenerator -> {
                val defaultValue = instance.range.endInclusive / 2.0f
                TextGeneratorValue.FloatValue(defaultValue)
            }
            is StringTextGenerator -> TextGeneratorValue.StringValue("")
            is SelectTextGenerator -> TextGeneratorValue.SelectValue(0)
            else -> null
        }
    }
}
