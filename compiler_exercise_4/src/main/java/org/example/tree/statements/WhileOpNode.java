package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.BodyOpNode;
import org.example.tree.expr.ExprOpNode;

public class WhileOpNode extends StatOpNode {

    public WhileOpNode(ExprOpNode exprOpNode, BodyOpNode bodyOpNode) {
        this.exprOpNode = exprOpNode;
        this.bodyOpNode = bodyOpNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public ExprOpNode exprOpNode;
    public BodyOpNode bodyOpNode;
}
