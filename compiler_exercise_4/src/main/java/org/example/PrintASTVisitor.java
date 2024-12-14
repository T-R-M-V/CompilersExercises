package org.example;

import org.example.tree.ProgramOpNode;

public class PrintASTVisitor implements Visitor {

    @Override
    public Object visit(ProgramOpNode programOpNode) {
        System.out.println("Hello World");
        return null;
    }

}
