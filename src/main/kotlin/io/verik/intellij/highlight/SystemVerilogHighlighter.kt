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
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.intellij.sdk.language.SystemVerilogLexerAdapter
import org.jetbrains.kotlin.idea.highlighter.KotlinHighlightingColors

class SystemVerilogHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return SystemVerilogLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        @Suppress("MISSING_DEPENDENCY_CLASS")
        val textAttributesKey : TextAttributesKey? = when (tokenType) {
            TokenType.WHITE_SPACE -> null
            SystemVerilogTokenTypes.LABEL_COMMENT -> KotlinHighlightingColors.LINE_COMMENT
            SystemVerilogTokenTypes.LINE_COMMENT -> KotlinHighlightingColors.LINE_COMMENT
            SystemVerilogTokenTypes.BLOCK_COMMENT -> KotlinHighlightingColors.BLOCK_COMMENT
            SystemVerilogTokenTypes.ATTRIBUTE -> KotlinHighlightingColors.ANNOTATION
            SystemVerilogTokenTypes.STRING -> KotlinHighlightingColors.STRING
            SystemVerilogTokenTypes.VALID_STRING_ESCAPE -> KotlinHighlightingColors.STRING_ESCAPE
            SystemVerilogTokenTypes.INVALID_STRING_ESCAPE -> KotlinHighlightingColors.INVALID_STRING_ESCAPE
            SystemVerilogTokenTypes.KEYWORD -> KotlinHighlightingColors.KEYWORD
            SystemVerilogTokenTypes.DIRECTIVE -> KotlinHighlightingColors.EXTENSION_FUNCTION_CALL
            SystemVerilogTokenTypes.IDENTIFIER -> KotlinHighlightingColors.LOCAL_VARIABLE
            SystemVerilogTokenTypes.UNSIGNED_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.DECIMAL_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.BINARY_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.OCTAL_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.HEX_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.TIME_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.REAL_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.FIXED_POINT_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.UNSIZED_LITERAL -> KotlinHighlightingColors.NUMBER
            SystemVerilogTokenTypes.SEMICOLON -> KotlinHighlightingColors.SEMICOLON
            SystemVerilogTokenTypes.PUNCTUATION -> KotlinHighlightingColors.DOT
            SystemVerilogTokenTypes.OPERATOR -> KotlinHighlightingColors.OPERATOR_SIGN
            else -> KotlinHighlightingColors.BAD_CHARACTER
        }
        return if (textAttributesKey != null) {
            arrayOf(textAttributesKey)
        } else arrayOf()
    }
}
