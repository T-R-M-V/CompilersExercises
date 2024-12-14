package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.expr.ExprOpNode;

public class ReturnOpNode extends StatOpNode {

    public ReturnOpNode(ExprOpNode exprOpNode) {
        this.exprOpNode = exprOpNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public ExprOpNode exprOpNode;
}
