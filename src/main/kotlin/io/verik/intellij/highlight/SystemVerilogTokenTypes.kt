/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.verik.intellij.highlight

object SystemVerilogTokenTypes {

    @JvmField val LABEL_COMMENT = SystemVerilogTokenType("labelComment")
    @JvmField val LINE_COMMENT = SystemVerilogTokenType("lineComment")
    @JvmField val BLOCK_COMMENT = SystemVerilogTokenType("blockComment")
    @JvmField val DOC_COMMENT = SystemVerilogTokenType("docComment")
    @JvmField val ATTRIBUTE = SystemVerilogTokenType("attribute")
    @JvmField val STRING = SystemVerilogTokenType("string")
    @JvmField val VALID_STRING_ESCAPE = SystemVerilogTokenType("validStringEscape")
    @JvmField val INVALID_STRING_ESCAPE = SystemVerilogTokenType("invalidStringEscape")
    @JvmField val KEYWORD = SystemVerilogTokenType("keyword")
    @JvmField val DIRECTIVE = SystemVerilogTokenType("directive")
    @JvmField val IDENTIFIER = SystemVerilogTokenType("identifier")
    @JvmField val UNSIGNED_LITERAL = SystemVerilogTokenType("unsignedLiteral")
    @JvmField val DECIMAL_LITERAL = SystemVerilogTokenType("decimalLiteral")
    @JvmField val BINARY_LITERAL = SystemVerilogTokenType("binaryLiteral")
    @JvmField val OCTAL_LITERAL = SystemVerilogTokenType("octalLiteral")
    @JvmField val HEX_LITERAL = SystemVerilogTokenType("hexLiteral")
    @JvmField val TIME_LITERAL = SystemVerilogTokenType("timeLiteral")
    @JvmField val REAL_LITERAL = SystemVerilogTokenType("realLiteral")
    @JvmField val FIXED_POINT_LITERAL = SystemVerilogTokenType("fixedPointLiteral")
    @JvmField val UNSIZED_LITERAL = SystemVerilogTokenType("unsizedLiteral")
    @JvmField val SEMICOLON = SystemVerilogTokenType("semicolon")
    @JvmField val PUNCTUATION = SystemVerilogTokenType("punctuation")
    @JvmField val OPERATOR = SystemVerilogTokenType("operator")
}
