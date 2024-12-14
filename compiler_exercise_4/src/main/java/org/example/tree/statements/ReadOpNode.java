package org.example.tree.statements;

import org.example.tree.IdentifierNode;

import java.util.List;

public class ReadOpNode extends StatOpNode {

    public ReadOpNode(List<IdentifierNode> identifierNodes) {
        this.identifierNodes = identifierNodes;
    }

    public List<IdentifierNode> identifierNodes;
}