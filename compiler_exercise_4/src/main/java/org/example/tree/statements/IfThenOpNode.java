package org.example.tree.statements;

import org.example.tree.BodyOpNode;
import org.example.tree.expr.ExprOpNode;

public class IfThenOpNode {

    public IfThenOpNode(ExprOpNode exprOpNode, BodyOpNode bodyOpNode) {
        this.exprOpNode = exprOpNode;
        this.bodyOpNode = bodyOpNode;
    }

    public ExprOpNode exprOpNode;
    public BodyOpNode bodyOpNode;
}
