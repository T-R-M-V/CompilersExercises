package org.example.tree;

public class TypeNode {

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

    public Type type;
}
