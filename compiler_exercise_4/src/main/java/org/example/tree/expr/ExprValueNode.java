package org.example.tree.expr;

import org.example.Visitor;
import org.example.tree.ConstantNode;
import org.example.tree.IdentifierNode;
import org.example.tree.statements.CallOpNode;

public class ExprValueNode extends ExprOpNode {

    public ExprValueNode(ConstantNode constantNode) {
        this.constantNode = constantNode;
        this.identifierNode = null;
        this.callOpNode = null;
    }

    public ExprValueNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
        this.constantNode = null;
        this.callOpNode = null;
    }

    public ExprValueNode(CallOpNode callOpNode) {
        this.callOpNode = callOpNode;
        this.constantNode = null;
        this.identifierNode = null;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public ConstantNode constantNode;
    public IdentifierNode identifierNode;
    public CallOpNode callOpNode;
}
