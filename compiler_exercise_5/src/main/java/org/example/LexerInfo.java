package org.example;

public class LexerInfo {

    public LexerInfo(int line, int column, Object value) {
        this.value = value;
        this.line = line + 1; // T: +1 because JavaCup starts to count from zero
        this.column = column + 1; // T: +1 because JavaCup starts to count from zero
    }

    public int line;
    public int column;
    public Object value;
}
