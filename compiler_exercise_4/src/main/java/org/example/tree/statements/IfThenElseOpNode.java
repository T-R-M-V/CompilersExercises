package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.BodyOpNode;
import org.example.tree.expr.ExprOpNode;

public class IfThenElseOpNode extends StatOpNode {

    public IfThenElseOpNode(ExprOpNode exprOpNode, BodyOpNode thenBodyOpNode, BodyOpNode elseBodyOpNode) {
        this.exprOpNode = exprOpNode;
        this.thenBodyOpNode = thenBodyOpNode;
        this.elseBodyOpNode = elseBodyOpNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


    public ExprOpNode exprOpNode;
    public BodyOpNode thenBodyOpNode;
    public BodyOpNode elseBodyOpNode;
}
