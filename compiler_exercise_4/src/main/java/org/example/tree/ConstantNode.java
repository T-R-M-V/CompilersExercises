package org.example.tree;

import org.example.Visitor;

public class ConstantNode extends Node {

    public enum Type {
        Integer,
        Boolean,
        Double,
        String,
        Char,
    }

    public ConstantNode(ConstantNode.Type type) {
        this.type = type;
        this.value = null;
    }

    public ConstantNode(ConstantNode.Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


    public ConstantNode.Type type;
    public String value;
}