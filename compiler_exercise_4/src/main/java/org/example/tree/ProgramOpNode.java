package org.example.tree;

import java.util.List;

public class ProgramOpNode {

    public ProgramOpNode(TempNode.TempDeclsNode declsTempNode, BeginEndOpNode beginEndOpNode) {
        varDeclOpNodes = declsTempNode.varDeclOpNodes;
        defDeclOpNodes = declsTempNode.defDeclOpNodes;
        this.beginEndOpNode = beginEndOpNode;
    }

    public List<VarDeclOpNode> varDeclOpNodes;
    public List<DefDeclOpNode> defDeclOpNodes;
    public BeginEndOpNode beginEndOpNode;
}
