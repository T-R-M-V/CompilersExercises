package org.example.tree;

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

    public ConstantNode.Type type;
    public String value;
}