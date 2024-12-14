package org.example.tree;

import java.util.List;

public class ParDeclOpNode extends Node {

    public ParDeclOpNode(List<PVarOpNode> pVarOpNodes, TypeNode typeNode) {
        this.pVarOpNodes = pVarOpNodes;
        this.typeNode = typeNode;
    }

    public List<PVarOpNode> pVarOpNodes;
    public TypeNode typeNode;
}
