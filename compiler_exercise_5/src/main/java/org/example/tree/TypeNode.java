package org.example.tree;

import org.example.Type;
import org.example.Visitor;

public class TypeNode extends Node{

    public TypeNode(Type type) {
        this.type = type;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public Type type;
}
