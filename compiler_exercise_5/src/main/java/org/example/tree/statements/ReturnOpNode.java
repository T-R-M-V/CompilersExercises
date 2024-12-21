package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.expr.ExprOpNode;

public class ReturnOpNode extends StatOpNode {

    public ReturnOpNode(ExprOpNode exprOpNode) {
        this.exprOpNode = exprOpNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public ExprOpNode exprOpNode;
}
