package org.example;

import java_cup.runtime.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        File fileToOpen = new File(args[0]);
        FileReader fir = new FileReader(fileToOpen);

        Lexer lexer = new Lexer(fir);
        Symbol symbol;
        int count = 0;
        do {
            symbol = lexer.next_token();
            System.out.print("<" + symbol.sym + "," + symbol.value + ">  ");

            count += 1;
            if (count == 5) {
                System.out.println();
                count = 0;
            }
        } while(symbol.sym != Token.EOF);

    }
}