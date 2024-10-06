package org.example;

import java.io.*;

public class Test {

    public static String tokensStreamOutputFileName = "tokens.txt";
    public static String symbolsTableOutputFileName = "symbolsTable.txt";

    public static void main(String[] args) {

        BufferedWriter tokensFileWriter = null;
        BufferedWriter symbolsTableWriter = null;
        try {

            File tokensFile = new File(tokensStreamOutputFileName);
            tokensFileWriter = new BufferedWriter(new FileWriter(tokensFile));

            File symbolsTableFile = new File(symbolsTableOutputFileName);
            symbolsTableWriter = new BufferedWriter(new FileWriter(symbolsTableFile));

            SymbolTable symbolTable = new SymbolTable();
            Lexer lexer = new Lexer(args[0], symbolTable);

            Token token;
            int count = 0;
            while((token = lexer.nextToken()) != null) {
                String output = token + " ";
                if(count == 5) {
                    count = 0;
                    output += "\n";
                }

                tokensFileWriter.write(output);
                System.out.print(output);

                count++;
            }

            // print symbols table (START)
            symbolsTableWriter.write(symbolTable.toString());
            // print symbols table (END)
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
                symbolsTableWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}