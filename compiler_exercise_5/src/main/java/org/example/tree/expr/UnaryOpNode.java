package org.example.tree.expr;

import org.example.Visitor;

public class UnaryOpNode extends ExprOpNode {

    public enum Type {
        Uminus,
        Not,
    }

    public UnaryOpNode(UnaryOpNode.Type type, ExprOpNode exprOpNode) {
        this.type = type;
        this.exprOpNode = exprOpNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public UnaryOpNode.Type type;
    public ExprOpNode exprOpNode;
}