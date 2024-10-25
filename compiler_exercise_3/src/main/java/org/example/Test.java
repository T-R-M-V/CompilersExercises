package org.example;

public class Test {
    public static void main(String[] args) {

        Lexer lexer = new Lexer();

        Parser parser = new Parser(lexer);

        if (parser.S() == true) {
            System.out.println("it's all good");
        }
        else {
            System.out.println("it's not all good");
        }

    }
}
