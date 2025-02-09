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

class HtmlEntitiesTextGenerator : SimpleTextGenerator() {
    override fun generate(input: String): String {
        return input.map { it.toHtmlEntity() }.joinToString("")
    }

    private fun Char.toHtmlEntity(): String {
        return RESERVED_SYMBOLS[this] ?: "&#${this.code};"
    }

    companion object {
        private val RESERVED_SYMBOLS = mapOf(
            '!' to "&excl;", '"' to "&quot;", '#' to "&num;",
            '%' to "&percnt;", '&' to "&amp;", '\'' to "&apos;",
            '(' to "&lpar;", ')' to "&rpar;", '*' to "&ast;",
            ',' to "&comma;", '.' to "&period;", '/' to "&sol;",
            ':' to "&colon;", ';' to "&semi;", '?' to "&quest;",
            '@' to "&commat;", '[' to "&lbrack;", '\\' to "&bsol;",
            ']' to "&rbrack;", '^' to "&Hat;", '_' to "&lowbar;",
            '`' to "&grave;", '{' to "&lbrace;", '|' to "&vert;",
            '}' to "&rbrace;", '~' to "&tilde;", ' ' to "&nbsp;",
            '¡' to "&iexcl;", '¦' to "&brvbar;", '§' to "&sect;",
            '¨' to "&uml;", '©' to "&copy;", 'ª' to "&ordf;",
            '«' to "&laquo;", '¬' to "&not;", '­' to "&shy;",
            '®' to "&reg;", '¯' to "&macr;", '²' to "&sup2;",
            '³' to "&sup3;", '´' to "&acute;", 'µ' to "&micro;",
            '¶' to "&para;", '·' to "&middot;", '¸' to "&cedil;",
            '¹' to "&sup1;", 'º' to "&ordm;", '»' to "&raquo;",
            '¿' to "&iquest;", '‐' to "&hyphen;", '–' to "&ndash;",
            '—' to "&mdash;", '―' to "&horbar;", '‖' to "&Vert;",
            '‘' to "&lsquo;", '’' to "&rsquo;", '‚' to "&sbquo;",
            '“' to "&ldquo;", '”' to "&rdquo;", '„' to "&bdquo;",
            '†' to "&dagger;", '‡' to "&Dagger;", '•' to "&bull;",
            '‥' to "&nldr;", '…' to "&hellip;", '‰' to "&permil;",
            '‱' to "&pertenk;", '′' to "&prime;", '″' to "&Prime;",
            '‴' to "&tprime;", '‵' to "&bprime;", '‹' to "&lsaquo;",
            '›' to "&rsaquo;", '‾' to "&oline;", '⁁' to "&caret;",
            '⁃' to "&hybull;", '⁄' to "&frasl;", '⁏' to "&bsemi;",
            '⁗' to "&qprime;", '™' to "&trade;",
        )
    }
}
