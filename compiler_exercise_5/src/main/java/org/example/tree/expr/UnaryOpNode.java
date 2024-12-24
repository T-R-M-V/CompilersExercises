package org.example.tree.expr;

import org.example.Visitor;

public class UnaryOpNode extends ExprOpNode {

    public enum Type {
        Uminus,
        Not,
    }

    public UnaryOpNode(UnaryOpNode.Type op, ExprOpNode exprOpNode) {
        this.op = op;
        this.exprOpNode = exprOpNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public UnaryOpNode.Type op;
    public ExprOpNode exprOpNode;
}