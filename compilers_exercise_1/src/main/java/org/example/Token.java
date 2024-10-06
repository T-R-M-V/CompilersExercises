package org.example;

public class Token {

    public enum Kind {
        ERROR,

        IDENTIFIER,
        NUM,

        IF,
        ELSE,
        WHILE,
        THEN,
        INT,
        FLOAT,

        LEFT_PAR,
        RIGHT_PAR,
        START_BLOCK,
        END_BLOCK,
        SEMI,

        REL_OP,
        ASSIGN,
    }

    public Token(Kind name) {
        this.name = name;
    }

    public Token(Kind name, String property) {
        this.name = name;
        this.property = property;
    }

    @Override
    public String toString() {
        return "<" + name + "," + property + ">";
    }

    public String property;
    public Kind name;
}
