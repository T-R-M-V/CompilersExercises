package org.example;
import java_cup.runtime.*;
import java.lang.StringBuilder;

%%

%public
%class Lexer
%cup

%unicode

%{
  StringBuilder buffer = new StringBuilder("");

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

Digit = [0-9]
NonZeroDigit = [1-9]
EndOfLine = \r|\n|\r\n
Whitespace = {EndOfLine} | [\t\f ]

CharacterForComment = [^\r\n]

IntPart = {NonZeroDigit}{Digit}*
FloatPart = \.{Digit}+
IntNumber = {IntPart} | 0
DoubleNumber = {IntPart}{FloatPart} | 0{FloatPart}

/* Id = [:jletter:] [:jletterdigit:]* */
Id = [A-Za-z] [A-Za-z0-9]*

%state COMMENT
%state STRING

/* CODE EXECUTED WHEN END OF FILE IS REACHED */
/* %eof{

    if (yystate() == STRING) {

    }
    else if (


%eof} */

%%

<YYINITIAL> {

    /*KEYWORD FOR PROGRAM*/
    "program"           {return symbol(sym.PROGRAM);}
    "begin"             {return symbol(sym.BEGIN);}
    "end"               {return symbol(sym.END);}

    /*KEYWORD FOR TYPES*/
    "int"               {return symbol(sym.INT);}
    "double"            {return symbol(sym.DOUBLE);}
    "bool"              {return symbol(sym.BOOL);}
    "string"            {return symbol(sym.STRING);}
    "char"              {return symbol(sym.CHAR);}

    /*KEYWORD FOR FUNCTIONS*/
    "def"               {return symbol(sym.DEF);}
    "return"            {return symbol(sym.RETURN);}
    "ref"               {return symbol(sym.REF);}

    /*KEYWORD FOR FLOW CONTROL*/
    "if"                {return symbol(sym.IF);}
    "then"              {return symbol(sym.THEN);}
    "while"             {return symbol(sym.WHILE);}
    "do"                {return symbol(sym.DO);}
    "else"              {return symbol(sym.ELSE);}

    /*REGULAR EXPR FOR NUMBERS' LITERALS*/
    {IntNumber}         {return symbol(sym.INT_CONST, yytext());}
    {DoubleNumber}      {return symbol(sym.DOUBLE_CONST, yytext());}

    /*KEYWORD FOR BOOLEANS' LITERALS*/
    "true"              {return symbol(sym.TRUE);}
    "false"             {return symbol(sym.FALSE);}

    /*KEYWORD FOR CHARS' LITERALS*/
    \'[^\']\'           {return symbol(sym.CHAR_CONST, yytext());}

    /*REGULAR EXPR FOR PUNCTUATION*/
    ";"                 {return symbol(sym.SEMI);}
    ":"                 {return symbol(sym.COLON);}
    ","                 {return symbol(sym.COMMA);}
    "|"                 {return symbol(sym.PIPE);}
    "{"                 {return symbol(sym.LBRAC);}
    "}"                 {return symbol(sym.RBRAC);}
    "("                 {return symbol(sym.LPAR);}
    ")"                 {return symbol(sym.RPAR);}

    /*KEYWORD FOR LOGICAL OPERATORS*/
    "not"               {return symbol(sym.NOT);}
    "and"               {return symbol(sym.AND);}
    "or"                {return symbol(sym.OR);}

    /*KEYWORD FOR IN,OUT OPERATORS*/
    "<<"                {return symbol(sym.IN);}
    ">>"                {return symbol(sym.OUT);}
    "!>>"               {return symbol(sym.OUTNL);}

    /*KEYWORD FOR ARITHMETIC OPERATORS*/
    "+"                 {return symbol(sym.PLUS);}
    "-"                 {return symbol(sym.MINUS);}
    "*"                 {return symbol(sym.TIMES);}
    "/"                 {return symbol(sym.DIV);}

    /*KEYWORD FOR ASSIGNING*/
    ":="                {return symbol(sym.ASSIGN);}
    "="                 {return symbol(sym.ASSIGNDECL);}

    /*KEYWORD FOR COMPARISON OPERATORS*/
    ">"                 {return symbol(sym.GT);}
    ">="                {return symbol(sym.GE);}
    "<"                 {return symbol(sym.LT);}
    "<="                {return symbol(sym.LE);}
    "=="                {return symbol(sym.EQ);}
    "<>"                {return symbol(sym.NE);}

    /*REGULAR EXPR FOR IDENTIFIERS*/
    {Id}                {return symbol(sym.ID, yytext());}

    /*REGULAR EXPR FOR WHITESPACES*/
    {Whitespace}        {   }




    /*REGULAR EXPR FOR BEGIN OF A STRING*/
    \"                  {buffer = new StringBuilder(""); yybegin(STRING);}

    /*REGULAR EXPR FOR BEGIN OF A COMMENT*/
    "//"{CharacterForComment}*{EndOfLine}?      { }
    "/*"                {yybegin(COMMENT);}
}

<STRING> {
    \"                  { yybegin(YYINITIAL); return symbol(sym.STRING_CONST,buffer.toString()); }
    [^\n\r\"\\]+        { buffer.append( yytext() ); }
    \\t                 { buffer.append('\t'); }
    \\n                 { buffer.append('\n'); }
    \\r                 { buffer.append('\r'); }
    \\\"                { buffer.append('\"'); }
    \\                  { buffer.append('\\'); }

    <<EOF>>             { return symbol(sym.error, "Stringa costante non completata"); }
}

/*TESTARE QUANTO RIESCE A CHIUDERLO BENE*/
<COMMENT> {
    "*/"                {yybegin(YYINITIAL);}
    <<EOF>>             { return symbol(sym.error, "Commento non chiuso");}
    [^]                 {   }
}

[^]         {  }