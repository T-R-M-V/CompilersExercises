package org.example.tree;

import org.example.Visitor;

import java.util.List;

public class ParDeclOpNode extends Node {

    public ParDeclOpNode(List<PVarOpNode> pVarOpNodes, TypeNode typeNode) {
        this.pVarOpNodes = pVarOpNodes;
        this.typeNode = typeNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }


    public List<PVarOpNode> pVarOpNodes;
    public TypeNode typeNode;
}
