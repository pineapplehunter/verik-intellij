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

WHITE_SPACE=[\R\ \n\t\f]
MODULE="module"

%%

<YYINITIAL> {MODULE}                                       { return SystemVerilogTokenTypes.MODULE; }

{WHITE_SPACE}+                                             { return TokenType.WHITE_SPACE; }

[^]                                                        { return TokenType.BAD_CHARACTER; }