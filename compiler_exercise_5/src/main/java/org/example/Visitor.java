package org.example;

import org.example.tree.*;
import org.example.tree.expr.BinaryOpNode;
import org.example.tree.expr.ExprOpNode;
import org.example.tree.expr.ExprValueNode;
import org.example.tree.expr.UnaryOpNode;
import org.example.tree.statements.*;

public interface Visitor {

    // T: For expressions (START)
    public Object visit(BinaryOpNode node);
    public Object visit(ExprOpNode node);
    public Object visit(ExprValueNode node);
    public Object visit(UnaryOpNode node);
    // T: For expressions (END)

    // T: For statements (START)
    public Object visit(AssignOpNode node);
    public Object visit(CallOpNode node);
    public Object visit(IfThenElseOpNode node);
    public Object visit(IfThenOpNode node);
    public Object visit(ReadOpNode node);
    public Object visit(ReturnOpNode node);
    public Object visit(StatOpNode node);
    public Object visit(WhileOpNode node);
    public Object visit(WriteOpNode node);
    // T: For statements (END)

    public Object visit(BeginEndOpNode node);
    public Object visit(BodyOpNode node);
    public Object visit(ConstantNode node);
    public Object visit(DefDeclOpNode node);
    public Object visit(IdentifierNode node);
    public Object visit(ParDeclOpNode node);
    public Object visit(ProgramOpNode node);
    public Object visit(PVarOpNode node);
    public Object visit(TypeNode node);
    public Object visit(TypeOrConstantNode node);
    public Object visit(VarDeclOpNode node);
    public Object visit(VarOptInitOpNode node);
}
