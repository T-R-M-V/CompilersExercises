package org.example.tree.statements;

import org.example.tree.BodyOpNode;
import org.example.tree.expr.ExprOpNode;

public class IfThenElseOpNode extends StatOpNode {

    public IfThenElseOpNode(ExprOpNode exprOpNode, BodyOpNode thenBodyOpNode, BodyOpNode elseBodyOpNode) {
        this.exprOpNode = exprOpNode;
        this.thenBodyOpNode = thenBodyOpNode;
        this.elseBodyOpNode = elseBodyOpNode;
    }

    public ExprOpNode exprOpNode;
    public BodyOpNode thenBodyOpNode;
    public BodyOpNode elseBodyOpNode;
}
