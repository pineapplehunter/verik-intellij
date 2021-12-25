package org.intellij.sdk.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import io.verik.intellij.highlight.SystemVerilogTokenTypes;
@SuppressWarnings("ALL")

%%

%class SystemVerilogLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

WHITE_SPACE = [ \n\r\t\f]

LABEL_COMMENT = `_\( [^\)]* \)
LINE_COMMENT = \/\/ [^\n\r]*
BLOCK_COMMENT = \/\* ([^\*] | (\*+[^\/]))* \*+\/

MODULE = "module"

%state STRING

%%

<YYINITIAL> {WHITE_SPACE}                          { return TokenType.WHITE_SPACE; }

<YYINITIAL> {LABEL_COMMENT}                        { return SystemVerilogTokenTypes.LABEL_COMMENT; }

<YYINITIAL> {LINE_COMMENT}                         { return SystemVerilogTokenTypes.LINE_COMMENT; }

<YYINITIAL> {BLOCK_COMMENT}                        { return SystemVerilogTokenTypes.BLOCK_COMMENT; }

<YYINITIAL> \"                                     { yybegin(STRING); return SystemVerilogTokenTypes.STRING; }

<STRING> \\ [nt\\\"vfa]                            { return SystemVerilogTokenTypes.VALID_STRING_ESCAPE; }

<STRING> \\ [0-7]{1,3}                             { return SystemVerilogTokenTypes.VALID_STRING_ESCAPE; }

<STRING> \\x [0-9a-fA-F]{1,2}                      { return SystemVerilogTokenTypes.VALID_STRING_ESCAPE; }

<STRING> \\ [^\n\r]                                { return SystemVerilogTokenTypes.INVALID_STRING_ESCAPE; }

<STRING> [^\n\r\"\\]+                              { return SystemVerilogTokenTypes.STRING; }

<STRING> \"                                        { yybegin(YYINITIAL); return SystemVerilogTokenTypes.STRING; }

<YYINITIAL> {MODULE}                               { return SystemVerilogTokenTypes.MODULE; }

[^]                                                { return TokenType.BAD_CHARACTER; }