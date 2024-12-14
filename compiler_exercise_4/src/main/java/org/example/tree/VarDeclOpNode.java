package org.example.tree;

import org.example.Visitor;

import java.util.List;

public class VarDeclOpNode extends Node {

    public VarDeclOpNode(List<VarOptInitOpNode> varOptInitOpNodes, TypeOrConstantNode typeOrConstantNode) {
        this.varOptInitOpNodes = varOptInitOpNodes;
        this.typeOrConstant = typeOrConstantNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public List<VarOptInitOpNode> varOptInitOpNodes;
    public TypeOrConstantNode typeOrConstant;
}
