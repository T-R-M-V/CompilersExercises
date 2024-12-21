package org.example;

import java.io.*;
import java_cup.runtime.Symbol;

public class LexerTesting {

    public static String tokensStreamOutputFileName = "tokens.txt";

    public static void main(String[] args) {

        BufferedWriter tokensFileWriter = null;

        try {

            File tokensFile = new File(tokensStreamOutputFileName);
            tokensFileWriter = new BufferedWriter(new FileWriter(tokensFile));

            Lexer lexer = new Lexer(new FileReader(args[0]));

            Symbol token;
            int count = 0;
            while(true) {
            // while((token = lexer.next_token()) != null) {
                // String output = token + " ";
                token = lexer.next_token();
                String output = "#" + sym.terminalNames[token.sym] + " ";

                if(count == 5) {
                    count = 0;
                    output += "\n";
                }

                tokensFileWriter.write(output);
                System.out.print(output);

                count++;

                if(token.sym == sym.EOF)
                    break;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }
        finally {
            try {
                tokensFileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
