package org.example.tree;

import org.example.Visitor;

import java.util.ArrayList;
import java.util.List;

public class VarDeclOpNode extends Node {

    public VarDeclOpNode(List<VarOptInitOpNode> varOptInitOpNodes, TypeOrConstantNode typeOrConstantNode) {
        this.varOptInitOpNodes = varOptInitOpNodes;
        this.typeOrConstant = typeOrConstantNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public List<VarOptInitOpNode> varOptInitOpNodes;
    public TypeOrConstantNode typeOrConstant;
}
