package org.example.tree;

import org.example.tree.statements.StatOpNode;

import java.util.List;

public class BeginEndOpNode extends Node {

    public BeginEndOpNode(TempNode.TempDeclsNode tempDeclsNode, List<StatOpNode> statOpNodes) {
        this.varDeclOpNodes = tempDeclsNode.varDeclOpNodes;
        this.statOpNodes = statOpNodes;
    }

    public List<VarDeclOpNode> varDeclOpNodes;
    public List<StatOpNode> statOpNodes;
}
