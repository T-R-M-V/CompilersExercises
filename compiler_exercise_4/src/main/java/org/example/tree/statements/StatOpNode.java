package org.example.tree.statements;

import org.example.Visitor;
import org.example.tree.Node;

public class StatOpNode extends Node {

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
