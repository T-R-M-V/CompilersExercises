package org.example;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public Lexer() {
        fakeTokens = new ArrayList<>();

        // fakeTokens.add(new Token())
    }

    public Token nextToken() {
        if (fakeTokens.size() > 0)
            return fakeTokens.remove(fakeTokens.size() - 1);
        return null;
    }

    private List<Token> fakeTokens;
}
