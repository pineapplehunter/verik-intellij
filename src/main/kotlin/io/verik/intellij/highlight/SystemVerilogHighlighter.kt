/*
 * Copyright (c) 2021 Francis Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.verik.intellij.highlight

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.intellij.sdk.language.SystemVerilogLexerAdapter

class SystemVerilogHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return SystemVerilogLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            SystemVerilogTokenTypes.LABEL_COMMENT -> COMMENT_KEYS
            SystemVerilogTokenTypes.LINE_COMMENT -> COMMENT_KEYS
            SystemVerilogTokenTypes.BLOCK_COMMENT -> COMMENT_KEYS
            SystemVerilogTokenTypes.STRING -> STRING_KEYS
            SystemVerilogTokenTypes.VALID_STRING_ESCAPE -> VALID_STRING_ESCAPE_KEYS
            SystemVerilogTokenTypes.INVALID_STRING_ESCAPE -> INVALID_STRING_ESCAPE_KEYS
            SystemVerilogTokenTypes.MODULE -> KEYWORD_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHARACTER_KEYS
            else -> EMPTY_KEYS
        }
    }

    companion object {

        val COMMENT_KEYS = arrayOf(
            createTextAttributesKey("LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        )
        val STRING_KEYS = arrayOf(
            createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING)
        )
        val VALID_STRING_ESCAPE_KEYS = arrayOf(
            createTextAttributesKey("VALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)
        )
        val INVALID_STRING_ESCAPE_KEYS = arrayOf(
            createTextAttributesKey("INVALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE)
        )
        val KEYWORD_KEYS = arrayOf(
            createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        )
        val BAD_CHARACTER_KEYS = arrayOf(
            createTextAttributesKey("BAD_CHARACTER")
        )
        val EMPTY_KEYS = arrayOf<TextAttributesKey>()
    }
}
