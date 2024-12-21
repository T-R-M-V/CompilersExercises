package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.expr.ExprOpNode;

import java.util.List;

public class WriteOpNode extends StatOpNode {

    public WriteOpNode(List<ExprOpNode> exprOpNodes, boolean newLine) {
        this.exprOpNodes = exprOpNodes;
        this.newLine = newLine;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }


    public List<ExprOpNode> exprOpNodes;
    public boolean newLine;
}
