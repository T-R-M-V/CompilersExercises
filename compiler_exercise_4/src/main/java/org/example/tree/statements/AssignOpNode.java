package org.example.tree.statements;

import org.example.tree.IdentifierNode;
import org.example.tree.expr.ExprOpNode;

import java.util.List;

public class AssignOpNode extends StatOpNode {

    public AssignOpNode(List<IdentifierNode> identifierNodes, List<ExprOpNode> exprNodes) {
        this.identifierNodes = identifierNodes;
        this.exprOpNodes = exprNodes;
    }

    public List<IdentifierNode> identifierNodes;
    public List<ExprOpNode> exprOpNodes;
}
