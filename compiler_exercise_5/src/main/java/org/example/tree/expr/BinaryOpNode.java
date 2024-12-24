package org.example.tree.expr;

import org.example.Visitor;

public class BinaryOpNode extends ExprOpNode {

    public enum Type {
        Plus, Minus, Times, Div,
        And, Or,
        Gt, Ge, Lt, Le, Eq, Ne,
    }

    public BinaryOpNode(BinaryOpNode.Type op, ExprOpNode left, ExprOpNode right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public BinaryOpNode.Type op;
    public ExprOpNode left;
    public ExprOpNode right;
}
