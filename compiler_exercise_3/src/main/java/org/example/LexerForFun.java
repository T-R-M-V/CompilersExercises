package org.example;

import java.util.ArrayList;
import java.util.List;

public class LexerForFun {

    public LexerForFun() {
        fakeTokens = new ArrayList<>();

        // file source1
        fakeTokens.add(new Token(Token.Kind.IF));
        fakeTokens.add(new Token(Token.Kind.IDENTIFIER));
        fakeTokens.add(new Token(Token.Kind.REL_OP));
        fakeTokens.add(new Token(Token.Kind.NUM));
        fakeTokens.add(new Token(Token.Kind.THEN));
        fakeTokens.add(new Token(Token.Kind.WHILE));
        fakeTokens.add(new Token(Token.Kind.IDENTIFIER));
        fakeTokens.add(new Token(Token.Kind.LOOP));
        fakeTokens.add(new Token(Token.Kind.IF));
        fakeTokens.add(new Token(Token.Kind.IDENTIFIER));
        fakeTokens.add(new Token(Token.Kind.REL_OP));
        fakeTokens.add(new Token(Token.Kind.IDENTIFIER));
        fakeTokens.add(new Token(Token.Kind.THEN));
        fakeTokens.add(new Token(Token.Kind.WHILE));
        fakeTokens.add(new Token(Token.Kind.IDENTIFIER));
        fakeTokens.add(new Token(Token.Kind.LOOP));
        fakeTokens.add(new Token(Token.Kind.IDENTIFIER));
        fakeTokens.add(new Token(Token.Kind.ASSIGN));
        fakeTokens.add(new Token(Token.Kind.IDENTIFIER));
        fakeTokens.add(new Token(Token.Kind.END));
        fakeTokens.add(new Token(Token.Kind.LOOP));
        // ----
        fakeTokens.add(new Token(Token.Kind.END));
        fakeTokens.add(new Token(Token.Kind.IF));
        fakeTokens.add(new Token(Token.Kind.END));
        fakeTokens.add(new Token(Token.Kind.LOOP));
        fakeTokens.add(new Token(Token.Kind.END));
        fakeTokens.add(new Token(Token.Kind.IF));
        fakeTokens.add(new Token(Token.Kind.EOF));
    }

    public Token nextToken() {
        if (fakeTokens.size() > 0)
            return fakeTokens.remove(0);
        return null;
    }

    private List<Token> fakeTokens;
}
