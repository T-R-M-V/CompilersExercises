package org.example.tree;

import org.example.Visitor;

import java.util.List;

public class ProgramOpNode extends Node{

    public ProgramOpNode(List<Node> declsNodes, BeginEndOpNode beginEndOpNode) {
        this.declsNodes = declsNodes;
        this.beginEndOpNode = beginEndOpNode;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public List<Node> declsNodes; // T: In this list are stored either the declaration of variables and functions
    public BeginEndOpNode beginEndOpNode;
}
