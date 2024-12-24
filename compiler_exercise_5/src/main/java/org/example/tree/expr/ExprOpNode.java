package org.example.tree.expr;

import org.example.Visitor;
import org.example.tree.Node;

public class ExprOpNode extends Node {
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public boolean withCallOp = false;
}
