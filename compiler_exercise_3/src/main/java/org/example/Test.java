package org.example;

import java.io.FileNotFoundException;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {

        SymbolTable symbolTable = new SymbolTable();
        Lexer lexer = new Lexer(args[0], symbolTable);

        Parser parser = new Parser(lexer);

        if (parser.S() == true) {
            System.out.println("it's all good");
        }
        else {
            System.out.println("it's not all good");
        }

    }
}
