package org.example.tree;

import java.util.List;

public class DefDeclOpNode extends Node {

    public DefDeclOpNode(IdentifierNode identifierNode, List<ParDeclOpNode> parDeclOpNodes, TypeNode typeNode, BodyOpNode bodyOpNode) {
        this.identifierNode = identifierNode;
        this.parDeclOpNodes = parDeclOpNodes;
        this.typeNode = typeNode;
        this.bodyOpNode = bodyOpNode;
    }

    public IdentifierNode identifierNode;
    public List<ParDeclOpNode> parDeclOpNodes;
    public TypeNode typeNode;
    public BodyOpNode bodyOpNode;
}
