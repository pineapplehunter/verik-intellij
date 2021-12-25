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
ATTRIBUTE = \(\* ([^\*] | (\*+[^\/]))* \*+\)

DIRECTIVE = (\`__FILE__ | \`__LINE__ | \`begin_keywords | \`celldefine | \`default_nettype | \`define | \`else |
\`elsif | \`end_keywords | \`endcelldefine | \`endif | \`ifdef | \`ifndef | \`include | \`line | \`nounconnected_drive |
\`pragma | \`resetall | \`timescale | \`unconnected_drive | \`undef | \`undefineall)

KEYWORD = ("module" | "accept_on" | "alias" | "always" | "always_comb" | "always_ff" | "always_latch" | "and" |
    "assert" | "assign" | "assume" | "automatic" | "before" | "begin" | "bind" | "bins" | "binsof" | "bit" | "break" |
    "buf" | "bufif0" | "bufif1" | "byte" | "case" | "casex" | "casez" | "cell" | "chandle" | "checker" | "class" |
    "clocking" | "cmos" | "config" | "const" | "constraint" | "context" | "continue" | "cover" | "covergroup" |
    "coverpoint" | "cross" | "deassign" | "default" | "defparam" | "design" | "disable" | "dist" | "do" | "edge" |
    "else" | "end" | "endcase" | "endchecker" | "endclass" | "endclocking" | "endconfig" | "endfunction" |
    "endgenerate" | "endgroup" | "endinterface" | "endmodule" | "endpackage" | "endprimitive" | "endprogram" |
    "endproperty" | "endspecify" | "endsequence" | "endtable" | "endtask" | "enum" | "event" | "eventually" | "expect" |
    "export" | "extends" | "extern" | "final" | "first_match" | "for" | "force" | "foreach" | "forever" | "fork" |
    "forkjoin" | "function" | "generate" | "genvar" | "global" | "highz0" | "highz1" | "if" | "iff" | "ifnone" |
    "ignore_bins" | "illegal_bins" | "implements" | "implies" | "import" "incdir" | "include" | "initial" "inout" |
    "input" | "inside" "instance" | "int" | "integer" "interconnect" | "interface" | "intersect" | "join" "join_any" |
    "join_none" | "large" | "let" | "liblist" | "library" | "local" | "localparam" | "logic" | "longint" |
    "macromodule" | "matches" | "medium" | "modport" | "module" | "nand" | "negedge" | "nettype" | "new" | "nexttime" |
    "nmos" | "nor" | "noshowcancelled" | "not" | "notif0" | "notif1" | "null" | "or" | "output" | "package" |
    "packed" | "parameter" | "pmos" | "posedge" | "primitive" | "priority" | "program" | "property" | "protected" |
    "pull0" | "pull1" | "pulldown" | "pullup" | "pulsestyle_ondetect" | "pulsestyle_onevent" | "pure"  | "rand" |
    "randc" | "randcase" | "randsequence" | "rcmos" | "real" | "realtime" | "ref" | "reg" | "reject_on" | "release" |
    "repeat" | "restrict" | "return" | "rnmos" | "rpmos" | "rtran" | "rtranif0" | "rtranif1" | "s_always" |
    "s_eventually" | "s_nexttime" | "s_until" | "s_until_with" | "scalared" | "sequence" | "shortint" | "shortreal" |
    "showcancelled" | "signed" | "small" | "soft" | "solve" | "specify" | "specparam" | "static" | "string" |
    "strong" | "strong0" | "strong1" | "struct" | "super" | "supply0" | "supply1" | "sync_accept_on" |
    "sync_reject_on" | "table" | "tagged" | "task" | "this" | "throughout" | "time" | "timeprecision" | "timeunit" |
    "tran" | "tranif0" | "tranif1" | "tri" | "tri0" | "tri1" | "triand" | "trior" | "trireg" | "type" | "typedef" |
    "union" | "unique" | "unique0" | "unsigned" | "until" | "until_with" | "untyped" | "use" | "uwire" | "var" |
    "vectored" | "virtual" | "void" | "wait" | "wait_order" | "wand" | "weak" | "weak0" | "weak1" | "while" |
    "wildcard" | "wire" | "with" | "within" | "wor" | "xnor" | "xor")

IDENTIFIER = [a-zA-Z_$][a-zA-Z0-9_$]*

SEMICOLON = ;

PUNCTUATION = ("," | "." | "(" | ")" | "[" | "]" | "{" | "}")

OPERATOR = ("+" | "-")

%state STRING

%%

<YYINITIAL> {WHITE_SPACE}                          { return TokenType.WHITE_SPACE; }

<YYINITIAL> {LABEL_COMMENT}                        { return SystemVerilogTokenTypes.LABEL_COMMENT; }

<YYINITIAL> {LINE_COMMENT}                         { return SystemVerilogTokenTypes.LINE_COMMENT; }

<YYINITIAL> {BLOCK_COMMENT}                        { return SystemVerilogTokenTypes.BLOCK_COMMENT; }

<YYINITIAL> {ATTRIBUTE}                            { return SystemVerilogTokenTypes.ATTRIBUTE; }

<YYINITIAL> \"                                     { yybegin(STRING); return SystemVerilogTokenTypes.STRING; }

<STRING> \\ [nt\\\"vfa]                            { return SystemVerilogTokenTypes.VALID_STRING_ESCAPE; }

<STRING> \\ [0-7]{1,3}                             { return SystemVerilogTokenTypes.VALID_STRING_ESCAPE; }

<STRING> \\x [0-9a-fA-F]{1,2}                      { return SystemVerilogTokenTypes.VALID_STRING_ESCAPE; }

<STRING> \\ [^\n\r]                                { return SystemVerilogTokenTypes.INVALID_STRING_ESCAPE; }

<STRING> [^\n\r\"\\]+                              { return SystemVerilogTokenTypes.STRING; }

<STRING> \"                                        { yybegin(YYINITIAL); return SystemVerilogTokenTypes.STRING; }

<YYINITIAL> {DIRECTIVE}                            { return SystemVerilogTokenTypes.DIRECTIVE; }

<YYINITIAL> {KEYWORD}                              { return SystemVerilogTokenTypes.KEYWORD; }

<YYINITIAL> {IDENTIFIER}                           { return SystemVerilogTokenTypes.IDENTIFIER; }

<YYINITIAL> {SEMICOLON}                            { return SystemVerilogTokenTypes.SEMICOLON; }

<YYINITIAL> {PUNCTUATION}                          { return SystemVerilogTokenTypes.PUNCTUATION; }

<YYINITIAL> {OPERATOR}                             { return SystemVerilogTokenTypes.OPERATOR; }

[^]                                                { return TokenType.BAD_CHARACTER; }