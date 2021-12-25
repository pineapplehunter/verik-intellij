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

object SystemVerilogTokenTypes {

    @JvmField val LABEL_COMMENT = SystemVerilogTokenType("labelComment")
    @JvmField val LINE_COMMENT = SystemVerilogTokenType("lineComment")
    @JvmField val BLOCK_COMMENT = SystemVerilogTokenType("blockComment")
    @JvmField val ATTRIBUTE = SystemVerilogTokenType("attribute")

    @JvmField val STRING = SystemVerilogTokenType("string")
    @JvmField val VALID_STRING_ESCAPE = SystemVerilogTokenType("validStringEscape")
    @JvmField val INVALID_STRING_ESCAPE = SystemVerilogTokenType("invalidStringEscape")

    @JvmField val DIRECTIVE = SystemVerilogTokenType("directive")
    @JvmField val KEYWORD = SystemVerilogTokenType("keyword")

    @JvmField val IDENTIFIER = SystemVerilogTokenType("identifier")
    @JvmField val SEMICOLON = SystemVerilogTokenType("semicolon")
    @JvmField val PUNCTUATION = SystemVerilogTokenType("punctuation")
    @JvmField val OPERATOR = SystemVerilogTokenType("operator")
}
