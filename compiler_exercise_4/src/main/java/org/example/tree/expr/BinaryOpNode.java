package org.example.tree.expr;

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

    public BinaryOpNode.Type type;
    public ExprOpNode left;
    public ExprOpNode right;
}
