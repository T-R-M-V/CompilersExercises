package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {

        try {
            SymbolTable symbolTable = new SymbolTable();
            Lexer lexer = new Lexer(args[0], symbolTable);

            Token token;
            while((token = lexer.nextToken()) != null) {
                System.out.print(token + " ");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }
    }
}