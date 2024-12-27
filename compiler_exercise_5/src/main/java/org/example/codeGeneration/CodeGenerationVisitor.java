package org.example.codeGeneration;

import org.example.Type;
import org.example.Visitor;
import org.example.scope.ScopeEntry;
import org.example.tree.*;
import org.example.tree.expr.BinaryOpNode;
import org.example.tree.expr.ExprOpNode;
import org.example.tree.expr.ExprValueNode;
import org.example.tree.expr.UnaryOpNode;
import org.example.tree.statements.*;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerationVisitor implements Visitor {

    @Override
    // T: REVIEW
    public Object visit(BinaryOpNode node) {

        String leftExpression = (String)node.left.accept(this);
        String rightExpression = (String)node.right.accept(this);

        Type resultType = node.type;
        // T: cast one of the two operand in String if it is necessary (START)
        if(resultType == Type.String && node.left.type != Type.String) {
            if(node.left.type == Type.Integer) {
                leftExpression = OperatorConverter.integerToString + "(" + leftExpression + ")";
            } else if(node.left.type == Type.Double) {
                leftExpression = OperatorConverter.doubleToString + "(" + leftExpression + ")";
            }
        }
        else if(resultType == Type.String && node.right.type != Type.String) {
            if(node.right.type == Type.Integer) {
                rightExpression = OperatorConverter.integerToString + "(" + rightExpression + ")";
            } else if(node.right.type == Type.Double) {
                rightExpression = OperatorConverter.doubleToString + "(" + rightExpression + ")";
            }
        }
        // T: cast one of the two operand in String if it is necessary (END)

        String operator = OperatorConverter.convertInC(node.op);
        // T: In the case we had Plus operator and we are operating among strings, call the concatenation
        // function.
        if(node.op == BinaryOpNode.Type.Plus && resultType == Type.String) {
            return OperatorConverter.plusStringOperator + "(" + leftExpression + "," + rightExpression + ")";
        }
        // T: In the case we had Div operator we must ensure that the second parameter of division is casted
        // to double to ensure that the result is calculated as a double.
        else if(node.op == BinaryOpNode.Type.Div && resultType == Type.Double) {
            return leftExpression + " /(double) (" + rightExpression + ")";
        }

        return leftExpression + " " + operator + " " + rightExpression;
    }

    @Override
    // T: this is never be called
    public Object visit(ExprOpNode node) {
        System.out.println("Never call this function");
        return null;
    }

    @Override
    public Object visit(ExprValueNode node) {

        if(node.identifierNode != null) {
            return node.identifierNode.accept(this);
        }

        if(node.constantNode != null) {
            return node.constantNode.accept(this);
        }

        if(node.callOpNode != null) {
            return node.callOpNode.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(UnaryOpNode node) {

        String argExpression = (String)node.exprOpNode.accept(this);
        String op = OperatorConverter.convertInC(node.op);

        return op + " " + argExpression;
    }

    @Override
    public Object visit(AssignOpNode node) {

        List<String> assignments = new ArrayList<>();

        for(int i = 0;i < node.identifierNodes.size();i++) {
            var identifierOpNode = node.identifierNodes.get(i);
            String identifierString = (String)identifierOpNode.accept(this);
            var exprOpNode = node.exprOpNodes.get(i);
            String exprOpNodeString = (String)exprOpNode.accept(this);

            String line = identifierString + " = " + exprOpNodeString + ";";
            if(exprOpNode.type == Type.String) {
                line = OperatorConverter.cloneString + "(" + identifierString + "," + exprOpNodeString + ")";
            }

            assignments.add(line);
        }

        return assignments;
    }

    @Override
    // T: WARNING we expect that all the identifier that are
    // reference are written adding '+'(excluding the String)
    public Object visit(CallOpNode node) {

        // T: Unused
        node.identifierNode.accept(this);

        ScopeEntry scopeEntry = node.scope.find(node.identifierNode.identifier);

        StringBuilder parametersString = new StringBuilder("");

        String comma = "";
        int index = 0;
        for(var exprOpNode : node.exprOpNodes) {
            if(index >= 1)
                comma = ",";

            String stringParameter = (String)exprOpNode.accept(this);
            var infoParameter = scopeEntry.parameters.get(index);

            if(infoParameter.type != Type.String) {
                if(infoParameter.ref) {
                    parametersString.append(comma + " &" + stringParameter);
                } else {
                    parametersString.append(comma + stringParameter);
                }
            } else {
                if(infoParameter.ref) {
                    parametersString.append(comma + stringParameter);
                } else {
                    parametersString.append(comma + OperatorConverter.cloneString + "(" + stringParameter + ")");
                }
            }

            index++;
        }

        return node.identifierNode.identifier + "(" + parametersString.toString() + ")";
    }

    @Override
    public Object visit(IfThenElseOpNode node) {

        List<String> ifThenElseLines = new ArrayList<>();

        String exprOpNodeString = (String)node.exprOpNode.accept(this);
        List<String> thenBodyLines = (List)node.thenBodyOpNode.accept(this);
        List<String> elseBodyLines = (List)node.elseBodyOpNode.accept(this);

        ifThenElseLines.add("if ( " + exprOpNodeString + " )");
        ifThenElseLines.add("{");
        ifThenElseLines.addAll(thenBodyLines);
        ifThenElseLines.add("}");
        ifThenElseLines.add("else {");
        ifThenElseLines.addAll(elseBodyLines);
        ifThenElseLines.add("}");

        return ifThenElseLines;
    }

    @Override
    public Object visit(IfThenOpNode node) {
        List<String> ifThenLines = new ArrayList<>();

        String exprOpNodeString = (String)node.exprOpNode.accept(this);
        List<String> thenBodyLines = (List)node.bodyOpNode.accept(this);

        ifThenLines.add("if ( " + exprOpNodeString + " )");
        ifThenLines.add("{");
        ifThenLines.addAll(thenBodyLines);
        ifThenLines.add("}");

        return ifThenLines;
    }

    @Override
    // T: we don't take space for sense of justice
    public Object visit(ReadOpNode node) {

        StringBuilder regexForScanf = new StringBuilder("");
        StringBuilder parameters = new StringBuilder("");

        String comma = "";
        for(var identifierNode : node.identifierNodes) {
            String identifierString = (String)identifierNode.accept(this);

            ScopeEntry scopeEntry = node.scope.find(identifierNode.identifier);

            if(scopeEntry.varType != Type.String) {
                identifierString = "&" + identifierString;
            }
            else {
                identifierString = "*" + identifierString;
            }
            parameters.append(comma + identifierString + " ");

            regexForScanf.append(OperatorConverter.pattern(scopeEntry.varType));

            comma = ",";
        }

        return "scanf( \"" + regexForScanf.toString() + "\", " + parameters.toString() + " );";
    }

    @Override
    public Object visit(ReturnOpNode node) {
        String identifierString = (String)node.accept(this);

        return "return " + identifierString + ";";
    }

    @Override
    // T: this is never be called
    public Object visit(StatOpNode node) {
        System.out.println("never called pocoaisjkdaì+");
        return null;
    }

    @Override
    public Object visit(WhileOpNode node) {

        List<String> lines = new ArrayList<>();

        String exprOpNodeString = (String)node.exprOpNode.accept(this);
        List<String> bodyLines = (List)node.bodyOpNode.accept(this);

        lines.add("while ( " + exprOpNodeString + " )");
        lines.add("{");
        lines.addAll(bodyLines);
        lines.add("}");

        return lines;
    }

    @Override
    public Object visit(WriteOpNode node) {

        String newLine = "";
        if(node.newLine)
            newLine = "\n";

        StringBuilder regexForPrintf = new StringBuilder("");
        StringBuilder parameters = new StringBuilder("");

        String comma = "";
        for(var exprOpNode : node.exprOpNodes) {
            String exprOpNodeString = (String)exprOpNode.accept(this);

            regexForPrintf.append(OperatorConverter.pattern(exprOpNode.type));
            if(exprOpNode.type == Type.String) {
                exprOpNodeString = comma + " *" + exprOpNodeString;
            }

            comma = ",";
        }

        return "printf( " + regexForPrintf.toString() + " " + newLine + " , " + parameters.toString() + ");";
    }

    @Override
    public Object visit(BeginEndOpNode node) {
        return null;
    }

    @Override
    public Object visit(BodyOpNode node) {
        return null;
    }

    @Override
    // T: to handle the string constant, wrap the value around a cloneString function
    public Object visit(ConstantNode node) {
        return null;
    }

    @Override
    public Object visit(DefDeclOpNode node) {
        return null;
    }

    @Override
    public Object visit(IdentifierNode node) {
        return null;
    }

    @Override
    public Object visit(ParDeclOpNode node) {
        return null;
    }

    @Override
    public Object visit(ProgramOpNode node) {
        return null;
    }

    @Override
    public Object visit(PVarOpNode node) {
        return null;
    }

    @Override
    public Object visit(TypeNode node) {
        return null;
    }

    @Override
    public Object visit(TypeOrConstantNode node) {
        return null;
    }

    @Override
    public Object visit(VarDeclOpNode node) {
        return null;
    }

    @Override
    public Object visit(VarOptInitOpNode node) {
        return null;
    }
}