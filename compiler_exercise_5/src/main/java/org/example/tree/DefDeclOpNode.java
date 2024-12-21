package org.example.tree;

import org.example.Visitor;

import java.util.List;

public class DefDeclOpNode extends Node {

    public DefDeclOpNode(IdentifierNode identifierNode, List<ParDeclOpNode> parDeclOpNodes, TypeNode typeNode, BodyOpNode bodyOpNode) {
        this.identifierNode = identifierNode;
        this.parDeclOpNodes = parDeclOpNodes;
        this.typeNode = typeNode;
        this.bodyOpNode = bodyOpNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }


    public IdentifierNode identifierNode;
    public List<ParDeclOpNode> parDeclOpNodes;
    public TypeNode typeNode;
    public BodyOpNode bodyOpNode;
}
