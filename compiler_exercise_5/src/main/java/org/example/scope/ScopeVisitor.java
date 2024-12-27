package org.example.scope;

import org.example.Type;
import org.example.Visitor;
import org.example.tree.*;
import org.example.tree.expr.BinaryOpNode;
import org.example.tree.expr.ExprOpNode;
import org.example.tree.expr.ExprValueNode;
import org.example.tree.expr.UnaryOpNode;
import org.example.tree.statements.*;

import org.example.error.Error;

import java.util.ArrayList;
import java.util.List;

public class ScopeVisitor implements Visitor {

    private Scope currentScope;
    private boolean isCalledFromDefDecl;

    public ScopeVisitor() {
        currentScope = null;
        isCalledFromDefDecl = false;
    }

    @Override
    public Object visit(BinaryOpNode node) {

        node.scope = currentScope;

        node.left.accept(this);
        currentScope = node.scope;

        node.right.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    // T: never called
    public Object visit(ExprOpNode node) {
        System.out.println("I will never be called");

        return null;
    }

    @Override
    public Object visit(ExprValueNode node) {
        node.scope = currentScope;

        if (node.constantNode != null) {
            node.constantNode.accept(this);
            currentScope = node.scope;
        }

        if (node.identifierNode != null) {
            node.identifierNode.accept(this);
            currentScope = node.scope;
        }

        if (node.callOpNode != null) {
            node.callOpNode.accept(this);
            currentScope = node.scope;
        }

        return null;
    }

    @Override
    public Object visit(UnaryOpNode node) {

        node.scope = currentScope;

        node.exprOpNode.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    public Object visit(AssignOpNode node) {
        node.scope = currentScope;

        for(var identifierNode: node.identifierNodes) {
            identifierNode.accept(this);
            currentScope = node.scope;
        }

        for(var exprOpNode : node.exprOpNodes) {
            exprOpNode.accept(this);
            currentScope = node.scope;
        }

        return null;
    }

    @Override
    public Object visit(CallOpNode node) {
        node.scope = currentScope;

        node.identifierNode.accept(this);
        currentScope = node.scope;

        for(var exprOpNode : node.exprOpNodes) {
            exprOpNode.accept(this);
            currentScope = node.scope;
        }

        return null;
    }

    @Override
    public Object visit(IfThenElseOpNode node) {
        node.scope = currentScope;

        node.thenBodyOpNode.accept(this);
        currentScope = node.scope;

        node.elseBodyOpNode.accept(this);
        currentScope = node.scope;

        node.exprOpNode.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    public Object visit(IfThenOpNode node) {
        node.scope = currentScope;

        node.exprOpNode.accept(this);
        currentScope = node.scope;

        node.bodyOpNode.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    public Object visit(ReadOpNode node) {

        node.scope = currentScope;

        for(var identifierNode: node.identifierNodes) {
            identifierNode.accept(this);
            currentScope = node.scope;
        }

        return null;
    }

    @Override
    public Object visit(ReturnOpNode node) {

        node.scope = currentScope;

        node.exprOpNode.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    // T: never called
    public Object visit(StatOpNode node) {
        System.out.println("I will never be called");

        return null;
    }

    @Override
    public Object visit(WhileOpNode node) {

        node.scope = currentScope;

        node.exprOpNode.accept(this);
        currentScope = node.scope;

        node.bodyOpNode.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    public Object visit(WriteOpNode node) {
        node.scope = currentScope;

        for(var exprOpNode : node.exprOpNodes) {
            exprOpNode.accept(this);
            currentScope = node.scope;
        }

        return null;
    }

    @Override
    public Object visit(BeginEndOpNode node) {

        // T: create the new scope (START)
        node.scope = new Scope();
        node.scope.setStartPos(node.line, node.column);
        node.scope.setParent( currentScope); // T: the scope is now set to ProgramOp scope
        // T: create the new scope (END)



        currentScope = node.scope; // T: current scope is now setted to BeginEndOp scope

        // T: add all the variable declaration to the BeginEndOp's scope (START)
        for(var varDecl : node.varDeclOpNodes) {
            varDecl.accept(this);
            currentScope = node.scope; // T: probably useless, the scope doesn't change visiting VarDecl
        }
        // T: add all the variable declaration to the BeginEndOp's scope (END)


        // T: set correctly the scope for all the statements (START)
        for(var statOpNode : node.statOpNodes) {
            statOpNode.accept(this);
            currentScope = node.scope; // T: probably useless, the scope doesn't change visiting VarDecl
        }
        // T: set correctly the scope for all the statements (END)


        return null;
    }

    @Override
    public Object visit(BodyOpNode node) {

        // T: Handle the difference between the Body of a function and the other Bodies (START)
        if(isCalledFromDefDecl) {
            isCalledFromDefDecl = false;
            node.scope = currentScope;
        } else {
            // T: Create a new scope if it's not the Body of a function
            node.scope = new Scope();
            node.scope.setParent(currentScope);
            node.scope.setStartPos(node.line, node.column);

            currentScope = node.scope;
        }
        // T: Handle the difference between the Body of a function and the other Bodies (END)


        // T: add all the variable declaration to the BeginEndOp's scope (START)
        for(var varDecl : node.varDeclOpNodes) {
            varDecl.accept(this);
            currentScope = node.scope; // T: probably useless, the scope doesn't change visiting VarDecl
        }
        // T: add all the variable declaration to the BeginEndOp's scope (END)


        // T: set correctly the scope for all the statements (START)
        for(var statOpNode : node.statOpNodes) {
            statOpNode.accept(this);
            currentScope = node.scope; // T: probably useless, the scope doesn't change visiting VarDecl
        }
        // T: set correctly the scope for all the statements (END)


        return null;
    }

    @Override
    public Object visit(ConstantNode node) {
        node.scope = currentScope;

        return null;
    }

    @Override
    public Object visit(DefDeclOpNode node) {

        // T: add the sign of procedure to the scope's table (START)
        String identifier = node.identifierNode.identifier;
        if(currentScope.scopeData.get(identifier) != null) {
            Error.stackError.add(new Error("The function is already defined", node.identifierNode.line, node.identifierNode.column));
            // System.exit(0);
        }

        List<ScopeEntry.TypeProcParameter> typeOfParameters = new ArrayList<>();
        for (var parDecl : node.parDeclOpNodes) {
            Type type = parDecl.typeNode.type;

            for (var varOp : parDecl.pVarOpNodes) {
                ScopeEntry.TypeProcParameter typeProcParameter = new ScopeEntry.TypeProcParameter(type, varOp.ref);
                typeOfParameters.add(typeProcParameter);
            }
        }

        ScopeEntry scopeEntry = ScopeEntry.createProcScope(node.typeNode.type, typeOfParameters);
        currentScope.scopeData.put(identifier, scopeEntry);
        // T: add the sign of procedure to the scope's table (END)





        node.scope = new Scope();
        node.scope.setParent(currentScope);
        node.scope.setStartPos(node.line, node.column);
        currentScope = node.scope;
        isCalledFromDefDecl = true;

        // T: Visit body
        node.bodyOpNode.accept(this);
        currentScope = node.scope; // T: now the current scope is of the body of the procedure

        for(var parDecl : node.parDeclOpNodes) {
            parDecl.accept(this);
            currentScope = node.scope; // T: probably useless, the scope doesn't change during the visit of this node
        }

        node.identifierNode.accept(this);
        currentScope = node.scope;

        node.typeNode.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    public Object visit(IdentifierNode node) {
        node.scope = currentScope;

        return null;
    }

    @Override
    public Object visit(ParDeclOpNode node) {

        node.scope = currentScope;

        Type type = node.typeNode.type;

        for(var varOp: node.pVarOpNodes) {

            String identifier = varOp.identifierNode.identifier;
            boolean ref = varOp.ref;

            if(currentScope.scopeData.get(identifier) == null) {
                ScopeEntry scopeEntry = ScopeEntry.createVarScope(type, ref);
                currentScope.scopeData.put(identifier, scopeEntry);
            } else { // T: Give error in the case in which the variable is already defined
                Error.stackError.add(new Error("Variable is already defined", varOp.identifierNode.line, varOp.identifierNode.column));
                // System.exit(0);
            }
        }


        node.typeNode.accept(this);
        currentScope = node.scope;

        for(var varOp: node.pVarOpNodes) {
            varOp.accept(this);
            currentScope = node.scope;
        }

        return null;
    }

    @Override
    public Object visit(ProgramOpNode node) {

        currentScope = new Scope();
        currentScope.setStartPos(node.line, node.column);
        node.scope = currentScope;

        for(var decl : node.declsNodes) {
            decl.accept(this);
            currentScope = node.scope;
        }

        node.beginEndOpNode.accept(this);
        currentScope = node.scope;

        return node.scope;
    }

    @Override
    public Object visit(PVarOpNode node) {
        node.scope = currentScope;

        node.identifierNode.accept(this);
        currentScope = node.scope;

        return null;
    }

    @Override
    public Object visit(TypeNode node) {
        node.scope = currentScope;

        return null;
    }

    @Override
    public Object visit(TypeOrConstantNode node) {

        node.scope = currentScope;

        if(node.constantNode != null) {
            node.constantNode.accept(this);
        } else {
            node.typeNode.accept(this);
        }
        currentScope = node.scope;

        return null;
    }

    @Override
    // T: For now we add variables to the scope even if there are errors in declaration
    public Object visit(VarDeclOpNode node) {

        node.scope = currentScope;

        // T: Check if the VarDeclOp respect some rules (START)
        if(node.typeOrConstant.constantNode != null) {
            int numberOfVariables = node.varOptInitOpNodes.size();

            if(numberOfVariables > 1) {
                Error.stackError.add(new Error("Can't define multiple variables when you use constant", node.line, node.column));
            }

            for(var varOptInit: node.varOptInitOpNodes) {
                if(varOptInit.exprOpNode != null) {
                    Error.stackError.add(new Error("Can't define an initial value for variables defined with a constant", varOptInit.line, varOptInit.column));
                }
            }
        }
        // T: Check if the VarDeclOp respect some rules (END)



        // T: type inference (START)
        Type type;
        if(node.typeOrConstant.constantNode != null) {
            type = node.typeOrConstant.constantNode.type;
        } else {
            type = node.typeOrConstant.typeNode.type;
        }
        // T: type inference (END)

        // T: retrieve the declared variables (START)
        for (var varOptInit : node.varOptInitOpNodes) {
            String identifier = varOptInit.identifierNode.identifier;
            if(node.scope.scopeData.get(identifier) == null) {
                ScopeEntry scopeEntry = ScopeEntry.createVarScope(type, false);
                node.scope.scopeData.put(identifier, scopeEntry);
            } else { // T: Error, already defined this variable in this scope
                Error.stackError.add(new Error("Already defined this variable in this scope", varOptInit.identifierNode.line, varOptInit.identifierNode.column));
                // System.exit(0);
            }
        }
        // T: retrieve the declared variables (END)



        node.typeOrConstant.accept(this);
        currentScope = node.scope;

        for(var varOptInit : node.varOptInitOpNodes) {
            varOptInit.accept(this);
            currentScope = node.scope;
        }

        return null;
    }

    @Override
    public Object visit(VarOptInitOpNode node) {

        node.scope = currentScope;

        node.identifierNode.accept(this);
        currentScope = node.scope;

        if (node.exprOpNode != null) {
            node.exprOpNode.accept(this);
            currentScope = node.scope;
        }

        return null;
    }
}
