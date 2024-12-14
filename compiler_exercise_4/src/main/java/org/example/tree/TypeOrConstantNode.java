package org.example.tree;

import org.example.Visitor;

public class TypeOrConstantNode extends Node {

    public TypeOrConstantNode(TypeNode typeNode) {
        this.typeNode = typeNode;
        this.constantNode = null;
    }

    public TypeOrConstantNode(ConstantNode constantNode) {
        this.typeNode = null;
        this.constantNode = constantNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


    public TypeNode typeNode;
    public ConstantNode constantNode;
}
