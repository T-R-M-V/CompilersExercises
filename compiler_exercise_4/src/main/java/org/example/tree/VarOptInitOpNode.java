package org.example.tree;

import org.example.Visitor;
import org.example.tree.expr.ExprOpNode;

public class VarOptInitOpNode extends Node {

    public VarOptInitOpNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
        exprOpNode = null;
    }

    public VarOptInitOpNode(IdentifierNode identifierNode, ExprOpNode exprOpNode) {
        this.identifierNode = identifierNode;
        this.exprOpNode = exprOpNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public IdentifierNode identifierNode;
    public ExprOpNode exprOpNode;
}
