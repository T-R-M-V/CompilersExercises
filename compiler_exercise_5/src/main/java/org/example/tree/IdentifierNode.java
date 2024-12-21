package org.example.tree;

import org.example.Visitor;

public class IdentifierNode extends Node {

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }


    public String identifier;
}
