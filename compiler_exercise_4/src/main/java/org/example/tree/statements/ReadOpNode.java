package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.IdentifierNode;

import java.util.List;

public class ReadOpNode extends StatOpNode {

    public ReadOpNode(List<IdentifierNode> identifierNodes) {
        this.identifierNodes = identifierNodes;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public List<IdentifierNode> identifierNodes;
}