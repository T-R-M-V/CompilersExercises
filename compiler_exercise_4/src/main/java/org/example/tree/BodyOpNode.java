package org.example.tree;

import org.example.tree.statements.StatOpNode;

import java.util.List;

public class BodyOpNode extends Node {

    public BodyOpNode(List<VarDeclOpNode> varDeclOpNodes, List<StatOpNode> statOpNodes) {
        this.varDeclOpNodes = varDeclOpNodes;
        this.statOpNodes = statOpNodes;
    }

    public List<VarDeclOpNode> varDeclOpNodes;
    public List<StatOpNode> statOpNodes;
}
