package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import static org.example.SymbolTable.Attribute;
import static org.example.SymbolTable.Kind;

public class Lexer {

    private RandomAccessFile file;
    private long begin;
    private long forward;
    private String lexem;

    private SymbolTable symbolTable;

    // STATES
    // 0: initial state
    // 1: i'm reading an identifier
    private int state;





    public Lexer(String inputFileName, SymbolTable symbolTable) throws FileNotFoundException {

        this.symbolTable = symbolTable;

        File file_stat = new File(inputFileName);
        file = new RandomAccessFile(file_stat, "r");
    }

    // TODO: verify if it is possible to put advanceBegin at the start of nextToken()
    public Token nextToken() throws IOException {

        boolean run = true;
        Character c;
        lexem = "";
        state = 0;

        while(run) {

            c = next_char();
            if (c == null) {
                run = false;
                c = ' ';
            }



            switch (state) {
                // Pattern matching for identifiers (START)
                case 0:
                    if (Character.isLetter(c)) {
                        state = 1;
                        lexem += c;
                    }
                    else if(is_non_zero_digit(c)) {
                        state = 6;
                        lexem += c;
                    }
                    else if(c == '0')
                    {
                        state = 12;
                        lexem += c;
                    }
                    else if (c == ';') {
                        advanceBegin();
                        return new Token(Token.Kind.SEMI);
                    }
                    else if(c == '{') {
                        advanceBegin();
                        return new Token(Token.Kind.START_BLOCK);
                    }
                    else if(c == '}') {
                        advanceBegin();
                        return new Token(Token.Kind.END_BLOCK);
                    }
                    else if(c == '(') {
                        advanceBegin();
                        return new Token(Token.Kind.LEFT_PAR);
                    }
                    else if(c == ')') {
                        advanceBegin();
                        return new Token(Token.Kind.RIGHT_PAR);
                    }
                    else if(c == '<' || c == '>') {
                        state = 2;
                        lexem += c;
                    }
                    else if(c == '=' || c == '!') {
                        state = 4;
                        lexem += c;
                    }
                    else if (Character.isWhitespace(c)) {
                        advanceBegin();
                        continue;
                    }

                    break;
                case 1:
                    if (is_sndLetter(c))
                    {
                       lexem += c;
                    }
                    else
                    {
                        retract();
                        advanceBegin();

                        Attribute attribute = symbolTable.getSymbol(lexem);
                        if (attribute != null)
                        {
                            if (attribute.kind == SymbolTable.Kind.RESERVED_WORD)
                                return new Token(Token.Kind.valueOf(attribute.token_name), null);
                            return new Token(Token.Kind.IDENTIFIER, Integer.toString(attribute.index));
                        }
                        else
                        {
                            int index = symbolTable.setSymbol(lexem, SymbolTable.Kind.IDENTIFIER);
                            return new Token(Token.Kind.IDENTIFIER, Integer.toString(index));
                        }
                    }
                    break;
                // Pattern matching for identifiers (END)



                // Pattern matching for operators (START)
                case 2:
                    if (c == '=') {
                        advanceBegin();
                        lexem += c;
                        return new Token(Token.Kind.REL_OP, lexem);
                    }
                    else if(c == '-') {
                        lexem += c;
                        state = 5;
                    }
                    else
                        state = 3;
                    break;

                case 3:
                    retract();
                    advanceBegin();

                    return new Token(Token.Kind.REL_OP, lexem);

                    //break; unreachable code

                case 4:
                    advanceBegin();

                    if (c == '=') {
                        lexem += c;
                        return new Token(Token.Kind.REL_OP, lexem);
                    }
                    else
                        return new Token(Token.Kind.ERROR);
                    //break; because is unreachable

                case 5:
                    if(c == '-') {
                        advanceBegin();
                        return new Token(Token.Kind.ASSIGN);
                    }
                    else
                        return new Token(Token.Kind.ERROR);

                    //break; because is unreachable
                // Pattern matching for operators (END)


                // Pattern matching for literals (START)
                case 6:

                    if(Character.isDigit(c)) {
                        lexem += c;
                    }
                    else if(c == '.') {
                        state = 7;
                        lexem += c;
                    }
                    else if(c == 'E') {
                        state = 9;
                        lexem += c;
                    }
                    else {
                        retract();
                        advanceBegin();
                        return generateNumToken(lexem);
                    }

                    break;
                case 7:

                    if(Character.isDigit(c)) {
                        lexem += c;
                        state = 8;
                    }
                    else {
                        return new Token(Token.Kind.ERROR);
                    }

                    break;
                case 8:

                    if(Character.isDigit(c)) {
                        lexem += c;
                    }
                    else if(c == 'E') {
                        state = 9;
                        lexem += c;
                    }
                    else {
                        retract();
                        advanceBegin();
                        return generateNumToken(lexem);
                    }

                    break;
                case 9:

                    if(c == '+' || c == '-') {
                        state = 10;
                        lexem += c;
                    }
                    else if(Character.isDigit(c)) {
                        state = 11;
                        lexem += c;
                    }
                    else {
                        return new Token(Token.Kind.ERROR);
                    }

                    break;
                case 10:

                    if(Character.isDigit(c))
                    {
                        state = 11;
                        lexem += c;
                    }
                    else
                    {
                        return new Token(Token.Kind.ERROR);
                    }

                    break;
                case 11:

                    if(Character.isDigit(c)) {
                        lexem += c;
                    }
                    else {
                        retract();
                        advanceBegin();
                        return generateNumToken(lexem);
                    }

                    break;
                case 12:

                    if(c == '.')
                    {
                        state = 7;
                        lexem += c;
                    }else {
                        retract();
                        advanceBegin();
                        return generateNumToken(lexem);
                    }

                    break;
                // Pattern matching for literals (END)
            }
        }

        return null;
    }

    private Character next_char() throws IOException {
        forward++;

        int next = file.read();
        if (next == -1)
            return null;
        return (char) next;
    }

    private void retract() throws IOException {
        forward--;
        file.seek(forward);
    }

    private void advanceBegin() {
        begin = forward;
    }

    private Token generateNumToken(String lexem) {
        Attribute attribute = symbolTable.getSymbol(lexem);
        int index;
        if(attribute == null)
        {
            index = symbolTable.setSymbol(lexem, SymbolTable.Kind.NUM);
        }
        else
        {
            index = attribute.index;
        }
        return new Token(Token.Kind.NUM, Integer.toString(index));
    }

    private static boolean is_sndLetter(Character c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    private static boolean is_non_zero_digit(Character c) {
        // Integer ascii_value = Character.getNumericValue(c);
        // return ascii_value >= Character.getNumericValue('1') && ascii_value <= Character.getNumericValue('9');
        return Character.isDigit(c) && c != '0';
    }
}