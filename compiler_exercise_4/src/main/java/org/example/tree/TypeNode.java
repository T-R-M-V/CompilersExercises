package org.example.tree;

import org.example.Visitor;

public class TypeNode extends Node{

    public enum Type {
        Integer,
        Boolean,
        Double,
        String,
        Char,
        Void,
    }

    public TypeNode(TypeNode.Type type) {
        this.type = type;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type type;
}
