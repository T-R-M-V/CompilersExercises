package org.example.tree;

import java.util.ArrayList;
import java.util.List;

public class VarDeclOpNode extends Node {

    public VarDeclOpNode(List<VarOptInitOpNode> varOptInitOpNodes, TypeOrConstantNode typeOrConstantNode) {
        this.varOptInitOpNodes = varOptInitOpNodes;
        this.typeOrConstant = typeOrConstantNode;
    }

    public List<VarOptInitOpNode> varOptInitOpNodes;
    public TypeOrConstantNode typeOrConstant;
}
