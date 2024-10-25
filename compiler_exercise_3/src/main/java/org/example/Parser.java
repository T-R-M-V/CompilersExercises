package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;





// T: TODO considerare il caso in cui venga restituito il Token Error
// T: TODO Re-enginering della soluzione attuale
public class Parser {

    enum Symbol {
        // NonTerminals
        S,
        Program,
        Program_1,
        Stmt,
        Expr,
        Expr_1,
        Rest,

        // Terminals
        EOF,
        SEMI,
        IF,
        THEN,
        ELSE,
        END,
        ID,
        ASSIGN,
        WHILE,
        LOOP,
        RELOP,
        NUM,

        EPSILON,
    }

    public Parser(Lexer lexer) {
        tokens = new ArrayList<>();
        currentTokenIndex = -1;

        this.lexer = lexer;

        SProduction = new ArrayList<>();
        // S -> Program EOF
        SProduction.add(Arrays.asList(Symbol.Program, Symbol.EOF));



        ProgramProduction = new ArrayList<>();
        // Program -> Stmt Program_1
        ProgramProduction.add(Arrays.asList(Symbol.Stmt, Symbol.Program_1));
        // Program -> Stmt
        ProgramProduction.add(Arrays.asList(Symbol.Expr, Symbol.Expr_1));



        Program_1Production = new ArrayList<>();
        // Program_1 -> ; Stmt Program_1
        Program_1Production.add(Arrays.asList(Symbol.SEMI, Symbol.Stmt, Symbol.Program_1));
        // Program_1 -> Epsilon
        Program_1Production.add(Arrays.asList(Symbol.EPSILON));



        StmtProduction = new ArrayList<>();
        // Stmt -> IF Expr Then Stmt Rest
        StmtProduction.add(Arrays.asList(Symbol.IF, Symbol.Expr, Symbol.THEN, Symbol.Stmt, Symbol.Rest));
        // Stmt -> ID ASSIGN Expr
        StmtProduction.add(Arrays.asList(Symbol.ID, Symbol.ASSIGN, Symbol.Expr));
        // Stmt -> WHILE Expr LOOP Stmt END LOOP
        StmtProduction.add(Arrays.asList(Symbol.WHILE, Symbol.Expr, Symbol.LOOP, Symbol.Stmt, Symbol.END, Symbol.LOOP));



        RestProduction = new ArrayList<>();
        // Rest -> END IF
        RestProduction.add(Arrays.asList(Symbol.END, Symbol.IF));
        // Rest -> ELSE Stmt END IF
        RestProduction.add(Arrays.asList(Symbol.ELSE, Symbol.Stmt, Symbol.END, Symbol.IF));



        ExprProduction = new ArrayList<>();
        // Expr -> ID Expr_1
        ExprProduction.add(Arrays.asList(Symbol.ID, Symbol.Expr_1));
        // Expr -> NUM Expr_1
        ExprProduction.add(Arrays.asList(Symbol.NUM, Symbol.Expr_1));



        Expr_1Production = new ArrayList<>();
        // Expr_1 -> RELOP Expr Expr_1
        Expr_1Production.add(Arrays.asList(Symbol.RELOP, Symbol.Expr, Symbol.Expr_1));
        // Expr_1 -> Epsilon
        Expr_1Production.add(Arrays.asList(Symbol.EPSILON));



        // Init non terminals (START)
        nonTerminalSymbols = new ArrayList<>();
        nonTerminalSymbols.add(Symbol.S);
        nonTerminalSymbols.add(Symbol.Program);
        nonTerminalSymbols.add(Symbol.Program_1);
        nonTerminalSymbols.add(Symbol.Stmt);
        nonTerminalSymbols.add(Symbol.Expr);
        nonTerminalSymbols.add(Symbol.Expr_1);
        nonTerminalSymbols.add(Symbol.Rest);
        // Init non terminals (END)
    };

    public boolean S() {

        int startingPoint = currentTokenIndex;
        Token token = nextToken();

        boolean accept = true;

        for (var production : SProduction) {

            accept = true;

            for (var symbol : production) {

                if (isNonTerminal(symbol)) {
                    if (callProdecure(symbol) == false) {
                        currentTokenIndex = startingPoint;
                        accept = false;
                        break;
                    }
                }
                else if (matchSymbolToken(symbol, tokens.get(currentTokenIndex))) {
                    if (symbol != Symbol.EOF)
                        nextToken();
                }
                else if (symbol != Symbol.EPSILON) {
                    currentTokenIndex = startingPoint;
                    accept = false;
                    break;
                }
            }

            if (accept)
                return true;
        }



        return false;
    }

    public boolean Program() {

        int startingPoint = currentTokenIndex;

        boolean accept = true;

        for (var production : ProgramProduction) {

            accept = true;

            for (var symbol : production) {

                if (isNonTerminal(symbol)) {
                    if (callProdecure(symbol) == false) {
                        currentTokenIndex = startingPoint;
                        accept = false;
                        break;
                    }
                }
                else if (matchSymbolToken(symbol, tokens.get(currentTokenIndex))) {
                    nextToken();
                }
                else if (symbol != Symbol.EPSILON) {
                    currentTokenIndex = startingPoint;
                    accept = false;
                    break;
                }
            }

            if (accept)
                return true;
        }

        return false;
    }

    public boolean Program_1() {

        int startingPoint = currentTokenIndex;

        boolean accept = true;

        for (var production : Program_1Production) {

            accept = true;

            for (var symbol : production) {

                if (isNonTerminal(symbol)) {
                    if (callProdecure(symbol) == false) {
                        currentTokenIndex = startingPoint;
                        accept = false;
                        break;
                    }
                }
                else if (matchSymbolToken(symbol, tokens.get(currentTokenIndex))) {
                    nextToken();
                }
                else if (symbol != Symbol.EPSILON) {
                    currentTokenIndex = startingPoint;
                    accept = false;
                    break;
                }
            }

            if (accept)
                return true;
        }

        return false;
    }

    public boolean Stmt() {

        int startingPoint = currentTokenIndex;

        boolean accept = true;

        for (var production : StmtProduction) {

            accept = true;

            for (var symbol : production) {

                if (isNonTerminal(symbol)) {
                    if (callProdecure(symbol) == false) {
                        currentTokenIndex = startingPoint;
                        accept = false;
                        break;
                    }
                }
                else if (matchSymbolToken(symbol, tokens.get(currentTokenIndex))) {
                    nextToken();
                }
                else if (symbol != Symbol.EPSILON) {
                    currentTokenIndex = startingPoint;
                    accept = false;
                    break;
                }
            }

            if (accept)
                return true;
        }

        return false;
    }

    public boolean Expr() {
        int startingPoint = currentTokenIndex;

        boolean accept = true;

        for (var production : ExprProduction) {

            accept = true;

            for (var symbol : production) {

                if (isNonTerminal(symbol)) {
                    if (callProdecure(symbol) == false) {
                        currentTokenIndex = startingPoint;
                        accept = false;
                        break;
                    }
                }
                else if (matchSymbolToken(symbol, tokens.get(currentTokenIndex))) {
                    nextToken();
                }
                else if (symbol != Symbol.EPSILON) {
                    currentTokenIndex = startingPoint;
                    accept = false;
                    break;
                }
            }

            if (accept)
                return true;
        }

        return false;
    }

    public boolean Expr_1() {
        int startingPoint = currentTokenIndex;

        boolean accept = true;

        for (var production : Expr_1Production) {

            accept = true;

            for (var symbol : production) {

                if (isNonTerminal(symbol)) {
                    if (callProdecure(symbol) == false) {
                        currentTokenIndex = startingPoint;
                        accept = false;
                        break;
                    }
                }
                else if (matchSymbolToken(symbol, tokens.get(currentTokenIndex))) {
                    nextToken();
                }
                else if (symbol != Symbol.EPSILON) {
                    currentTokenIndex = startingPoint;
                    accept = false;
                    break;
                }
            }

            if (accept)
                return true;
        }

        return false;
    }

    public boolean Rest() {
        int startingPoint = currentTokenIndex;

        boolean accept = true;

        for (var production : RestProduction) {

            accept = true;

            for (var symbol : production) {

                if (isNonTerminal(symbol)) {
                    if (callProdecure(symbol) == false) {
                        currentTokenIndex = startingPoint;
                        accept = false;
                        break;
                    }
                }
                else if (matchSymbolToken(symbol, tokens.get(currentTokenIndex))) {
                    nextToken();
                }
                else if (symbol != Symbol.EPSILON) {
                    currentTokenIndex = startingPoint;
                    accept = false;
                    break;
                }
            }

            if (accept)
                return true;
        }

        return false;
    }





    public Token nextToken() {
        if (currentTokenIndex == tokens.size() - 1) {
            Token token = null;
            try {
                token = lexer.nextToken();
                System.out.println(token);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            currentTokenIndex++;
            tokens.add(token);
            return token;
        }

        currentTokenIndex++;
        return tokens.get(currentTokenIndex);
    }

    public boolean callProdecure(Symbol symbol) {

        if (symbol == Symbol.S) {
            return S();
        }

        if (symbol == Symbol.Program) {
            return Program();
        }

        if (symbol == Symbol.Program_1) {
            return Program_1();
        }

        if (symbol == Symbol.Stmt) {
            return Stmt();
        }

        if (symbol == Symbol.Expr) {
            return Expr();
        }

        if (symbol == Symbol.Expr_1) {
            return Expr_1();
        }

        if (symbol == Symbol.Rest) {
            return Rest();
        }


        // Useless
        return false;
    }

    public boolean matchSymbolToken(Symbol symbol, Token token) {
        if (symbol == Symbol.ID && token.name == Token.Kind.IDENTIFIER) {
            return true;
        }

        if (symbol == Symbol.NUM && token.name == Token.Kind.NUM) {
            return true;
        }

        if (symbol == Symbol.IF && token.name == Token.Kind.IF) {
            return true;
        }

        if (symbol == Symbol.ELSE && token.name == Token.Kind.ELSE) {
            return true;
        }

        if (symbol == Symbol.THEN && token.name == Token.Kind.THEN) {
            return true;
        }

        if (symbol == Symbol.ASSIGN && token.name == Token.Kind.ASSIGN) {
            return true;
        }

        if (symbol == Symbol.WHILE && token.name == Token.Kind.WHILE) {
            return true;
        }

        if (symbol == Symbol.LOOP && token.name == Token.Kind.LOOP) {
            return true;
        }

        if (symbol == Symbol.RELOP && token.name == Token.Kind.REL_OP) {
            return true;
        }

        if (symbol == Symbol.SEMI && token.name == Token.Kind.SEMI) {
            return true;
        }

        if (symbol == Symbol.EOF && token.name == Token.Kind.EOF) {
            return true;
        }

        if (symbol == Symbol.END && token.name == Token.Kind.END) {
            return true;
        }

        return false;
    }

    public boolean isNonTerminal(Symbol symbol) {
        return nonTerminalSymbols.contains(symbol);
    }

    private int currentTokenIndex;
    private List<Token> tokens;
    private Lexer lexer;

    private List<Symbol> nonTerminalSymbols;

    private List<List<Symbol>> SProduction;
    private List<List<Symbol>> ProgramProduction;
    private List<List<Symbol>> Program_1Production;
    private List<List<Symbol>> ExprProduction;
    private List<List<Symbol>> Expr_1Production;
    private List<List<Symbol>> StmtProduction;
    private List<List<Symbol>> RestProduction;
}