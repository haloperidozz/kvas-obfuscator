package com.github.haloperidozz.obfuscator.util

// https://ru.wikipedia.org/wiki/ISO_9
enum class ISO9 {
    Simple {
        override fun transliterate(text: String): String {
            return text.applyMapping(SIMPLE_MAPPING)
        }
    },
    Standard {
        override fun transliterate(text: String): String {
            return text.applyMapping(STANDARD_MAPPING).replace(C_REGEX) { match ->
                val c = match.groupValues[1]
                val following = match.groupValues[2]

                if (following.isNotEmpty()) {
                    if (c == "Ц") "C$following" else "c$following"
                } else {
                    if (c == "Ц") "CZ" else "cz"
                }
            }
        }
    };

    abstract fun transliterate(text: String): String

    companion object {
        private val SIMPLE_MAPPING = mapping(
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d",
            'е' to "e", 'ё' to "yo", 'ж' to "j", 'з' to "z", 'и' to "i",
            'й' to "y", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n",
            'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t",
            'у' to "u", 'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch",
            'ш' to "sh", 'щ' to "sch", 'ъ' to "", 'ы' to "i", 'ь' to "'",
            'э' to "e", 'ю' to "yu", 'я' to "ya"
        )

        private val STANDARD_MAPPING = mapping(
            'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d",
            'е' to "e", 'ё' to "yo", 'ж' to "zh", 'з' to "z", 'и' to "i",
            'й' to "j", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n",
            'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t",
            'у' to "u", 'ф' to "f", 'х' to "x",

            // Keep 'ц' unchanged for special processing.
            'ц' to "ц",

            'ч' to "ch", 'ш' to "sh", 'щ' to "shh", 'ъ' to "``", 'ы' to "y`",
            'ь' to "`", 'э' to "e`", 'ю' to "yu", 'я' to "ya",
            '’' to "'"
        )

        // It is recommended to use "C" before the letters I, E, Y, J
        // and "CZ" otherwise.
        private val C_REGEX = Regex("([Цц])([IEYJieyj])?")

        private fun String.applyMapping(mapping: Map<Char, String>): String {
            return this.map { mapping[it] ?: it.toString() }.joinToString(separator = "")
        }

        private fun mapping(vararg mapping: Pair<Char, String>): Map<Char, String> {
            return mapping.flatMap { (key, value) ->
                listOf(
                    key.lowercaseChar() to value.lowercase(),
                    key.uppercaseChar() to value.uppercase()
                )
            }.toMap()
        }
    }
}