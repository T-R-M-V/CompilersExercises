package org.example.tree;

public class TypeOrConstantNode extends Node {

    public TypeOrConstantNode(TypeNode typeNode) {
        this.typeNode = typeNode;
        this.constantNode = null;
    }

    public TypeOrConstantNode(ConstantNode constantNode) {
        this.typeNode = null;
        this.constantNode = constantNode;
    }

    public TypeNode typeNode;
    public ConstantNode constantNode;
}
