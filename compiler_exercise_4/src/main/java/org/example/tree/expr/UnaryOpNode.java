package org.example.tree.expr;

public class UnaryOpNode extends ExprOpNode {

    public enum Type {
        Uminus,
        Not,
    }

    public UnaryOpNode(UnaryOpNode.Type type, ExprOpNode exprOpNode) {
        this.type = type;
        this.exprOpNode = exprOpNode;
    }

    public UnaryOpNode.Type type;
    public ExprOpNode exprOpNode;
}