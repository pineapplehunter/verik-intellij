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

LABEL_COMMENT = "`_(" [^)]* ")"
LINE_COMMENT = "//" [^\r\n]*
BLOCK_COMMENT = "/*" ([^*] | (\*+[^*/]))* \*+"/"

STRING = \"([^\\\"]|\\.)*\"

MODULE = "module"

%%

{WHITE_SPACE}                                              { return TokenType.WHITE_SPACE; }

{LABEL_COMMENT}                                            { return SystemVerilogTokenTypes.LABEL_COMMENT; }

{LINE_COMMENT}                                             { return SystemVerilogTokenTypes.LINE_COMMENT; }

{BLOCK_COMMENT}                                            { return SystemVerilogTokenTypes.BLOCK_COMMENT; }

{STRING}                                                   { return SystemVerilogTokenTypes.STRING; }

{MODULE}                                                   { return SystemVerilogTokenTypes.MODULE; }

[^]                                                        { return TokenType.BAD_CHARACTER; }