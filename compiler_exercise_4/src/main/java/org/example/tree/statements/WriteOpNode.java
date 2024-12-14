package org.example.tree.statements;

import org.example.tree.expr.ExprOpNode;

import java.util.List;

public class WriteOpNode extends StatOpNode {

    public WriteOpNode(List<ExprOpNode> exprOpNodes, boolean newLine) {
        this.exprOpNodes = exprOpNodes;
        this.newLine = newLine;
    }

    public List<ExprOpNode> exprOpNodes;
    public boolean newLine;
}
