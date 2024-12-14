package org.example.tree;

import org.example.Visitor;

public class PVarOpNode extends Node {

    public PVarOpNode(IdentifierNode identifierNode) {
        this.ref = false;
        this.identifierNode = identifierNode;
    }

    public PVarOpNode(IdentifierNode identifierNode, boolean ref) {
        this.ref = ref;
        this.identifierNode = identifierNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public IdentifierNode identifierNode;
    public boolean ref;
}
