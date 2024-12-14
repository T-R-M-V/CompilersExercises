package org.example.tree;

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

    public IdentifierNode identifierNode;
    public ExprOpNode exprOpNode;
}
