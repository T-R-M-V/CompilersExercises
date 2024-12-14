package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.BodyOpNode;
import org.example.tree.expr.ExprOpNode;

public class IfThenOpNode extends StatOpNode {

    public IfThenOpNode(ExprOpNode exprOpNode, BodyOpNode bodyOpNode) {
        this.exprOpNode = exprOpNode;
        this.bodyOpNode = bodyOpNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public ExprOpNode exprOpNode;
    public BodyOpNode bodyOpNode;
}
