package org.example.tree;

import org.example.Type;
import org.example.Visitor;

public class ConstantNode extends Node {

    public ConstantNode(Type type) {
        this.type = type;
        this.value = null;
    }

    public ConstantNode(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }


    public Type type;
    public String value;
}