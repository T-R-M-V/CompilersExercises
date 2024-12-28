package org.example.tree.expr;

import org.example.Visitor;
import org.example.tree.Node;

public class ExprOpNode extends Node {
    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    // T: withCallOp is used to check if there is a function call in an expression
    // T: this is necessary for the check among the multiple assigning that can't use
    // function call when multiple variables are assigned.
    public boolean withCallOp = false;
}
