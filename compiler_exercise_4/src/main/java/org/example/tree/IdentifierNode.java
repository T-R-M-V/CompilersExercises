package org.example.tree;

import org.example.Visitor;

public class IdentifierNode extends Node {

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


    public String identifier;
}
