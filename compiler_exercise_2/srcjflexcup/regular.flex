package org.example;
import java_cup.runtime.*;

%%

%public
%class Lexer
%cupsym Token
%cup

%unicode

%{
  String buffer = "";

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

Letter = [a-zA-Z]
Digit = [0-9]
NonZeroDigit = [1-9]
EndOfLine = [\r|\n|\r\n]
Whitespace = {EndOfLine} | [\t\f ]

Identifier = {Letter}[{Letter}|{Digit}|_]*
IntPart = {NonZeroDigit}{Digit}*
FloatPart = \.{Digit}+
ExpPart = E[+-]?{Digit}+
Number = {IntPart}{FloatPart}?{ExpPart}? | 0{FloatPart}?{ExpPart}?

Types = "int" | "float"

Relop = "<=" | ">=" | "==" | ">" | "<"
Op = "+" | "-" | "*" | "/"

%%

<YYINITIAL> {

    "if"                { return symbol(Token.IF);}
    "else"             { return symbol(Token.ELSE);}
    "while"             { return symbol(Token.WHILE);}
    "then"              { return symbol(Token.THEN);}
    ";"                 { return symbol(Token.SEMI);}
    ","                 { return symbol(Token.COMMA);}
    "("                 { return symbol(Token.LPAR); }
    ")"                 { return symbol(Token.RPAR); }
    "{"                 { return symbol(Token.LBRACK); }
    "}"                 { return symbol(Token.RBRACK); }
    "<--"               { return symbol(Token.ASSIGN); }

    {Types}             { return symbol(Token.TYPE, yytext());}

    {Relop}             { return symbol(Token.RELOP, yytext());}
    {Op}                { return symbol(Token.OP, yytext()); }

    {Identifier}        { return symbol(Token.IDENTIFIER, yytext()); }
    {Number}            { return symbol(Token.NUMBER, yytext());}

    {Whitespace}        { /*ignore*/ }
}

[^]         { return symbol(Token.ERROR, yytext()); }