package org.example.tree.statements;

import org.example.tree.expr.ExprOpNode;

public class ReturnOpNode extends StatOpNode {

    public ReturnOpNode(ExprOpNode exprOpNode) {
        this.exprOpNode = exprOpNode;
    }

    public ExprOpNode exprOpNode;
}
