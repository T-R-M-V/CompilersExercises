package org.example.tree.expr;

import org.example.Visitor;
import org.example.tree.Node;

public class ExprOpNode extends Node {
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
