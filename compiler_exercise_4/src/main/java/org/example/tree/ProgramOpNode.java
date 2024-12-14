package org.example.tree;

import org.example.Visitor;

import java.util.List;

public class ProgramOpNode extends Node{

    public ProgramOpNode(TempNode.TempDeclsNode declsTempNode, BeginEndOpNode beginEndOpNode) {
        varDeclOpNodes = declsTempNode.varDeclOpNodes;
        defDeclOpNodes = declsTempNode.defDeclOpNodes;
        this.beginEndOpNode = beginEndOpNode;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


    public List<VarDeclOpNode> varDeclOpNodes;
    public List<DefDeclOpNode> defDeclOpNodes;
    public BeginEndOpNode beginEndOpNode;
}
