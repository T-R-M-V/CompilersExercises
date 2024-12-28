package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.IdentifierNode;
import org.example.tree.expr.ExprOpNode;

import java.util.ArrayList;
import java.util.List;

public class CallOpNode extends StatOpNode {

    public CallOpNode(IdentifierNode identifierNode, List<ExprOpNode> exprOpNodes) {
        this.identifierNode = identifierNode;
        this.exprOpNodes = exprOpNodes;
    }

    public CallOpNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
        this.exprOpNodes = new ArrayList<>();
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public IdentifierNode identifierNode;
    public List<ExprOpNode> exprOpNodes;

    public boolean isStatement;
}
