package org.example.tree.expr;

import org.example.Visitor;

public class BinaryOpNode extends ExprOpNode {

    public enum Type {
        Plus, Minus, Times, Div,
        And, Or,
        Gt, Ge, Lt, Le, Eq, Ne,
    }

    public BinaryOpNode(BinaryOpNode.Type type, ExprOpNode left, ExprOpNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public BinaryOpNode.Type type;
    public ExprOpNode left;
    public ExprOpNode right;
}
