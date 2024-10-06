package org.example;

import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    public static enum Kind {
        IDENTIFIER,
        RESERVED_WORD,
        NUM,
    }

    public static class Attribute {
        public Kind kind;

        // use that only in the case the lexem is a reserved words
        public String token_name;

        // index of identifiers/costants
        public Integer index;

        public Attribute(Kind kind, String token_name, Integer index) {
            this.token_name = token_name;
            this.kind = kind;
            this.index = index;
        }

        @Override
        public String toString() {
            String indexToString = "null";
            if (index != null)
                indexToString = index.toString();

            return "[" + kind + "," + indexToString + "," + token_name + "]";
        }
    }

    public SymbolTable() {
        symbols = new HashMap<>();

        // Add all the reserved words (START)
        symbols.put("if", new Attribute(Kind.RESERVED_WORD, "IF", null));
        symbols.put("else", new Attribute(Kind.RESERVED_WORD, "ELSE", null));
        symbols.put("while", new Attribute(Kind.RESERVED_WORD, "WHILE", null));
        symbols.put("then", new Attribute(Kind.RESERVED_WORD, "THEN", null));
        symbols.put("int", new Attribute(Kind.RESERVED_WORD, "INT", null));
        symbols.put("float", new Attribute(Kind.RESERVED_WORD, "FLOAT", null));
        // Add all the reserved words (END)

        this.progressiveIndex = 0;
    }

    public int setSymbol(String key, Kind kind) {
        int actualIndex = progressiveIndex;

        Attribute attribute = new Attribute(kind, null, actualIndex);
        progressiveIndex++;

        symbols.put(key, attribute);

        return actualIndex;
    }

    public Attribute getSymbol(String key) {
        return symbols.get(key);
    }

    @Override
    public String toString() {
        String stringBuffer = "";
        for(Map.Entry<String, Attribute> entry : symbols.entrySet()) {
            stringBuffer += entry.getKey() + "=" + entry.getValue().toString() + "\n";
        }
        return stringBuffer;
    }

    private HashMap<String, Attribute> symbols;
    private int progressiveIndex;
}
