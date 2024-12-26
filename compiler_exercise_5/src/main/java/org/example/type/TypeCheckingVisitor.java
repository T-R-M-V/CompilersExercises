package org.example.type;

import org.example.Type;
import org.example.Visitor;
import org.example.error.Error;
import org.example.scope.ScopeEntry;
import org.example.tree.*;
import org.example.tree.expr.BinaryOpNode;
import org.example.tree.expr.ExprOpNode;
import org.example.tree.expr.ExprValueNode;
import org.example.tree.expr.UnaryOpNode;
import org.example.tree.statements.*;

import java.util.ArrayList;
import java.util.List;

public class TypeCheckingVisitor implements Visitor {

    // T: NOTES
    // T: identifierNode.type not setted, because we verify the value setted in the scope only after

    // T: this buffer is used to take notes of the return encountered during the visiting
    // of a function or the Begin...End part of the program.
    public List<ReturnOpNode> returnOpNodesOfLastFunction;

    public TypeCheckingVisitor() {
        returnOpNodesOfLastFunction = new ArrayList<>();
    }


    @Override
    public Object visit(BinaryOpNode node) {

        Type typeLeft = (Type)node.left.accept(this);
        Type typeRight = (Type)node.right.accept(this);

        Type resultType = TypeSystemOperator.getTypeBinaryOperator(node.op, typeLeft, typeRight);

        if(node.left.withCallOp || node.right.withCallOp) {
            node.withCallOp = true;
        }

        node.type = resultType;
        if(typeLeft != Type.Error && typeRight != Type.Error && resultType == Type.Error) {
            // T: launch error
            String errorMessage = "Can't use " + node.op + " with expression of type: " + typeLeft + " and " + typeRight;
            Error.launchError(errorMessage, node.line, node.column);
        }

        return node.type;
    }

    @Override
    // T: this is never called
    public Object visit(ExprOpNode node) {
        System.out.println("this never be called");
        return null;
    }

    @Override
    public Object visit(ExprValueNode node) {

        if(node.constantNode != null) {
            node.withCallOp = false;

            node.type = (Type)node.constantNode.accept(this);
        }

        if(node.identifierNode != null) {
            node.withCallOp = false;

            ScopeEntry entry = node.scope.find(node.identifierNode.identifier);
            // T: WARNING missing check if the identifier isn't defined

            if(entry.kind == ScopeEntry.Kind.Var) {
                node.type = entry.varType;
            }
            // T: return error if the identifier refer to a function
            // T: WARNING we don't know if this is necessary
            else {
                // T: launch error
                String errorMessage = "The identifier: " + node.identifierNode.identifier + " isn't a variable";
                Error.launchError(errorMessage, node.line, node.column);

                node.type = Type.Error;
            }
        }

        if(node.callOpNode != null) {
            node.withCallOp = true;

            node.type = (Type)node.callOpNode.accept(this);
        }

        return node.type;
    }

    @Override
    public Object visit(UnaryOpNode node) {

        Type typeArg = (Type)node.exprOpNode.accept(this);

        Type resultType = TypeSystemOperator.getTypeUnaryOperator(node.op, typeArg);

        if(node.exprOpNode.withCallOp)
            node.withCallOp = true;

        node.type = resultType;
        if(typeArg != Type.Error && resultType == Type.Error) {
            // T: launch error
            String errorMessage = "Can't use " + node.op + " with expression of type: " + typeArg;
            Error.launchError(errorMessage, node.line, node.column);
        }

        return node.type;
    }

    @Override
    public Object visit(AssignOpNode node) {

        for(var identifierNode : node.identifierNodes) {
            identifierNode.accept(this);
        }

        // T: omitted errors, redundant
        for(var exprOpNode : node.exprOpNodes) {
            Type typeExprOpNode = (Type)exprOpNode.accept(this);

            // T: checking if there is a function call in the exprOpNode
            // when there are multiple exprOpNode.
            if(node.exprOpNodes.size() > 1 && exprOpNode.withCallOp) {
                // T: launch error
                String errorMessage = "Can't use the return value of a function in multiple assignment";
                Error.launchError(errorMessage, exprOpNode.line, exprOpNode.column);

                node.type = Type.Error;
            }
        }

        node.type = Type.Void;

        if(node.exprOpNodes.size() != node.identifierNodes.size()) {
            // T: launch error
            String errorMessage = "The number of values and variables isn't equal";
            Error.launchError(errorMessage, node.line, node.column);

            node.type = Type.Error;
        }

        for(int i = 0;i < Math.min(node.exprOpNodes.size(), node.identifierNodes.size());i++) {
            var exprOpNode = node.exprOpNodes.get(i);
            var identifierNode = node.identifierNodes.get(i);

            ScopeEntry entry = node.scope.find(identifierNode.identifier);
            // T: WARNING: miss the control if the variable is defined
            if(entry.kind == ScopeEntry.Kind.Var) {
                // T: check if the variable and the expr has the same type
                if(entry.varType != exprOpNode.type) {
                    // T: launch error
                    // T: Review: add the types
                    String errorMessage = "Type mismatch between the identifier: " + identifierNode.identifier + " and the expression at: (" + exprOpNode.line + "," + exprOpNode.column + ")";
                    Error.launchError(errorMessage, identifierNode.line, identifierNode.column);

                    node.type = Type.Error;
                }
            } else
            // T: return error if the identifier is referred to a function
            {
                // T: Launch error
                String errorMessage = "The identifier: " + identifierNode.identifier + " isn't a variable";
                Error.launchError(errorMessage, identifierNode.line, identifierNode.column);

                node.type = Type.Error;
            }
        }

        return node.type;
    }

    @Override
    // T: WARNING rewrite this piece of shit
    public Object visit(CallOpNode node) {

        ScopeEntry entry = node.scope.find(node.identifierNode.identifier);
        // T: WARNING miss the control if the variable is defined
        if(entry.kind == ScopeEntry.Kind.Proc) {
            node.type = entry.returnType;
        }
        else
        // T: check if the identifier is referred to a variable
        {
            // T: launch error
            String errorMessage = "The identifier: " + node.identifierNode.identifier + " isn't a function/procedure";
            Error.launchError(errorMessage, node.line, node.column);

            node.type = Type.Error;

            // T: if referred to a variable, we must exit from this procedure, there is no sense
            // in returning other errors.
            return node.type;
        }

        // T: check if the number of parameters is the same of the definition (START)
        if(node.exprOpNodes.size() != entry.parameters.size() ) {
            // T: launch error
            String errorMessage = "The number of parameters of the function and the number of parameter provided in the call mismatch";
            Error.launchError(errorMessage, node.line, node.column);

            node.type = Type.Error;
        }
        // T: check if the number of parameters is the same of the definition (END)

        // T: check if the type of parameters is coherent with the definition (START)
        int index = 0;
        for(var exprOpNode : node.exprOpNodes) {
            Type typeExprOpNode = (Type)exprOpNode.accept(this);
            if(typeExprOpNode == Type.Error) {
                // T: WARNING emit error, redundant
                node.type = Type.Error;
            }
            else {
                // T: return error if the type of expression is the same of the parameter (START)
                if(typeExprOpNode != entry.parameters.get(index).type) {
                    // T: launch error
                    String errorMessage = "Type mismatch between the value passed in the function call and the definition of the function";
                    Error.launchError(errorMessage, exprOpNode.line, exprOpNode.column);

                    node.type = Type.Error;
                }
                // T: return error if the type of expression is the same of the parameter (END)

                // T: check if the parameter is a reference and the expression node is a variable (START)
                if(entry.parameters.get(index).ref) {
                    if(exprOpNode instanceof ExprValueNode) {
                        ExprValueNode castedExprOpNode = (ExprValueNode)exprOpNode;
                        if(castedExprOpNode.identifierNode == null) {
                            // T: launch error
                            String errorMessage = "The parameter of the function is defined like a reference, an expression is passed";
                            Error.launchError(errorMessage, exprOpNode.line, exprOpNode.column);

                            node.type = Type.Error;
                        }
                    }
                }
                // T: check if the parameter is a reference and the expression node  is a variable(END)
            }

            index++;
        }
        // T: check if the type of parameters is coherent with the definition (END)

        return node.type;
    }

    @Override
    public Object visit(IfThenElseOpNode node) {

        node.type = Type.Void;

        node.exprOpNode.accept(this);
        if(node.exprOpNode.type != Type.Error && node.exprOpNode.type != Type.Boolean) {
            // T: launch error
            String errorMessage = "The type of the condition of an If must be boolean";
            Error.launchError(errorMessage, node.exprOpNode.line, node.exprOpNode.column);

            node.type = Type.Error;
        }

        node.thenBodyOpNode.accept(this);
        if(node.thenBodyOpNode.type != Type.Void) {
            // T: launch error
            node.type = Type.Error;
        }

        node.elseBodyOpNode.accept(this);
        if(node.elseBodyOpNode.type != Type.Void) {
            // T: launche error
            node.type = Type.Error;
        }

        return node.type;
    }

    @Override
    public Object visit(IfThenOpNode node) {

        node.type = Type.Void;

        node.exprOpNode.accept(this);
        if(node.exprOpNode.type != Type.Error && node.exprOpNode.type != Type.Boolean) {
            // T: launch error
            String errorMessage = "The type of the condition of an If must be boolean";
            Error.launchError(errorMessage, node.exprOpNode.line, node.exprOpNode.column);

            node.type = Type.Error;
        }

        node.bodyOpNode.accept(this);
        if(node.bodyOpNode.type != Type.Void) {
            // T: launch error
            node.type = Type.Error;
        }

        return node.type;
    }

    @Override
    public Object visit(ReadOpNode node) {

        node.type = Type.Void;

        for(var identifierNode : node.identifierNodes) {
            identifierNode.accept(this);

            ScopeEntry scopeEntry = node.scope.find(identifierNode.identifier);
            // T: WARNING miss the control if the identifier exist

            // T: return error if the identifier is referred to a function
            if(scopeEntry.kind == ScopeEntry.Kind.Proc) {
                // T: launch error
                String errorMessage = "The identifier: " + identifierNode.identifier + " isn't a variable";
                Error.launchError(errorMessage, identifierNode.line, identifierNode.column);

                node.type = Type.Error;
            }
        }

        return node.type;
    }

    @Override
    // T: the errors for putting in the wrong place the return isn't returned here
    public Object visit(ReturnOpNode node) {

        node.exprOpNode.accept(this);

        node.type = node.exprOpNode.type;

        returnOpNodesOfLastFunction.add(node);

        return node.type;
    }

    @Override
    // T: this is never called
    public Object visit(StatOpNode node) {
        System.out.println("Never call me");
        return null;
    }

    @Override
    public Object visit(WhileOpNode node) {

        node.type = Type.Void;

        Type typeExprOpNode = (Type)node.exprOpNode.accept(this);
        if(node.exprOpNode.type != Type.Error && typeExprOpNode != Type.Boolean) {
            // T: launch error
            String errorMessage = "The type of the condition of a while must be boolean";
            Error.launchError(errorMessage, node.exprOpNode.line, node.exprOpNode.column);

            node.type = Type.Error;
        }

        Type typeBodyOpNode = (Type)node.bodyOpNode.accept(this);
        if(typeBodyOpNode != Type.Void) {
            // T: WARNING omitted error
            node.type = Type.Error;
        }

        return node.type;
    }

    @Override
    public Object visit(WriteOpNode node) {

        node.type = Type.Void;

        for(var exprOpNode : node.exprOpNodes) {
            Type typeExprOpNode = (Type)exprOpNode.accept(this);

            if(typeExprOpNode == Type.Error) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }

            // T: NOTE the type of expression is void only if is represented by a procedure call
            if(typeExprOpNode == Type.Void) {
                // T: launch error (you can't use a procedure)
                String errorMessage = "Can't use a procedure call like input to a write, procedure hasn't a returning value";
                Error.launchError(errorMessage, exprOpNode.line, exprOpNode.column);

                node.type = Type.Void;
            }
        }

        return node.type;
    }

    @Override
    public Object visit(BeginEndOpNode node) {

        node.type = Type.Void;

        // T: clear the buffer of ReturnOpNodes
        returnOpNodesOfLastFunction.clear();

        for(var varDeclOpNode : node.varDeclOpNodes) {
            Type typeVarDeclOpNode = (Type)varDeclOpNode.accept(this);
            if(typeVarDeclOpNode != Type.Void) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }
        }

        for(var statOpNode : node.statOpNodes) {
            Type typeStatOpNode = (Type)statOpNode.accept(this);
            if(typeStatOpNode != Type.Void) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }
        }

        // T: return error if there is any return in Begin...End
        if(! returnOpNodesOfLastFunction.isEmpty()) {
            // T: launch error
            // T: we launch an error for each return we found in Begin...End
            for(var returnOpNode : returnOpNodesOfLastFunction) {
                String errorMessage = "You can't use return outside of a function";
                Error.launchError(errorMessage, returnOpNode.line, returnOpNode.column);
            }

            node.type = Type.Error;
        }

        return node.type;
    }

    @Override
    public Object visit(BodyOpNode node) {

        node.type = Type.Void;

        for(var varDeclOpNode : node.varDeclOpNodes) {
            Type typeVarDeclOpNode = (Type)varDeclOpNode.accept(this);
            if(typeVarDeclOpNode != Type.Void) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }
        }

        for(var statOpNode : node.statOpNodes) {
            Type typeStatOpNode = (Type)statOpNode.accept(this);
            if(typeStatOpNode == Type.Error) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }
        }

        return node.type;
    }

    @Override
    // T: type is already setted during parsing for this node
    public Object visit(ConstantNode node) {
        return node.type;
    }

    @Override
    public Object visit(DefDeclOpNode node) {

        node.type = Type.Void;

        // T: clean the buffer of ReturnOpNodes
        returnOpNodesOfLastFunction.clear();

        node.identifierNode.accept(this);

        // T: WARNING for now no error is found in this case
        for(var parDeclOpNode : node.parDeclOpNodes) {
            parDeclOpNode.accept(this);
        }

        Type typeBodyOpNode = (Type)node.bodyOpNode.accept(this);
        // T: WARNING error omitted because redundant
        if(typeBodyOpNode != Type.Void) {
            node.type = Type.Error;
        }

        Type returnType = (Type)node.typeNode.accept(this);

        if(returnType == Type.Error) {
            // T: WARNING omitted error
            node.type = Type.Error;
            return node.type;
        }

        if(returnType != Type.Void) {
            // T: check if there is at least one return in this function
            if(returnOpNodesOfLastFunction.isEmpty()) {
                // T: launch error
                String errorMessage = "Must provide at least one return statement in a function";
                Error.launchError(errorMessage, node.line, node.column);

                node.type = Type.Error;
            }

            // T: check if the return type is coherent with the return type of the function (START)
            for(var returnOpNode : returnOpNodesOfLastFunction) {
                if(returnOpNode.type != returnType) {
                    // T: launch error
                    String errorMessage = "Type mismatch between type of return statement and the type provided in the declaration of function";
                    Error.launchError(errorMessage, returnOpNode.line, returnOpNode.column);

                    node.type = Type.Error;
                }
            }
            // T: check if the return type is coherent with the return type of the function (END)
        }
        else {
            // T: return error if there is any return in a procedure
            if(! returnOpNodesOfLastFunction.isEmpty()) {
                // T: launch error
                // T: we launch an error for each return we found in the procedure
                for(var returnOpNode : returnOpNodesOfLastFunction) {
                    String errorMessage = "You can't use return outside of a function";
                    Error.launchError(errorMessage, returnOpNode.line, returnOpNode.column);
                }

                node.type = Type.Error;
            }
        }

        return node.type;
    }

    @Override
    // T: we return void because doesn't make sense to return the value in the scope table
    public Object visit(IdentifierNode node) {
        return Type.Void;
    }

    @Override
    public Object visit(ParDeclOpNode node) {

        node.type = Type.Void;

        Type typeTypeNode = (Type)node.typeNode.accept(this);


        for(var pVarOpNode : node.pVarOpNodes) {
            Type typePVarOpNode = (Type)pVarOpNode.accept(this);
            if(typePVarOpNode != Type.Void) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }
        }

        return node.type;
    }

    @Override
    public Object visit(ProgramOpNode node) {

        node.type = Type.Void;

        Type typeBeginEndOpNode = (Type)node.beginEndOpNode.accept(this);
        if(typeBeginEndOpNode != Type.Void) {
            // T: WARNING omitted error
            node.type = Type.Error;
        }

        for(var declsNode : node.declsNodes) {
            Type typeDeclsNode = (Type)declsNode.accept(this);
            if(typeDeclsNode != Type.Void) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }
        }

        return node.type;
    }

    @Override
    public Object visit(PVarOpNode node) {
        node.identifierNode.accept(this);

        return Type.Void;
    }

    @Override
    // T: Theorically the type is already assigned to this node(during the parsing)
    public Object visit(TypeNode node) {
        return node.type;
    }

    @Override
    public Object visit(TypeOrConstantNode node) {

        if(node.typeNode != null) {
            node.typeNode.accept(this);
            node.type = node.typeNode.type;
        }

        if(node.constantNode != null) {
            node.constantNode.accept(this);
            node.type = node.constantNode.type;
        }

        return node.type;
    }

    @Override
    // T: To review
    public Object visit(VarDeclOpNode node) {

        node.type = Type.Void;

        // T: visit typeOrConstant (START)
        if(node.typeOrConstant.constantNode != null) {
            node.typeOrConstant.constantNode.accept(this);
        }
        if(node.typeOrConstant.typeNode != null) {
            node.typeOrConstant.typeNode.accept(this);
        }

        if(node.typeOrConstant.type == Type.Error) {
            // T: WARNING omitted error
            node.type = Type.Error;
        }
        // T: visit typeOrConstant (END)

        // T: visit the VarOptInitiOpNodes (START)
        for(var varOptInitOpNode : node.varOptInitOpNodes) {
            Type typeVarOptInitOpNode = (Type)varOptInitOpNode.accept(this);
            if(typeVarOptInitOpNode == Type.Error) {
                // T: WARNING omitted error
                node.type = Type.Error;
            }
        }
        // T: visit the VarOptInitiOpNodes (END)

        // T: To review
        if(node.typeOrConstant.constantNode != null) {
            if (node.varOptInitOpNodes.get(0).type == Type.Void) {
                node.varOptInitOpNodes.get(0).type = node.typeOrConstant.type;
            }
        }

        if(node.typeOrConstant.typeNode != null) {
            Type type = node.typeOrConstant.type;
            for(var varOptInitOpNode : node.varOptInitOpNodes) {

                // T: we put void as type of varOptInitOpNode when we don't provide an expression
                // to varOptInitOpNode.
                if(varOptInitOpNode.type == Type.Void) {
                    varOptInitOpNode.type = type;
                    continue;
                }

                if(varOptInitOpNode.type != type) {
                    // T: Launch error
                    String errorMessage = "Type mismatch between the identifier: " + varOptInitOpNode.identifierNode.identifier + " and the expression at: (" + varOptInitOpNode.exprOpNode.line + "," + varOptInitOpNode.exprOpNode.column + ")";
                    Error.launchError(errorMessage, varOptInitOpNode.identifierNode.line, varOptInitOpNode.identifierNode.column);

                    node.type = Type.Error;
                }
            }
        }

        return node.type;
    }

    @Override
    public Object visit(VarOptInitOpNode node) {

        node.type = Type.Void;

        node.identifierNode.accept(this);

        if(node.exprOpNode != null) {
            node.type = (Type)node.exprOpNode.accept(this);
            if(node.type == Type.Error) {
                // T: WARNING omitted error
            }
        }

        return node.type;
    }
}