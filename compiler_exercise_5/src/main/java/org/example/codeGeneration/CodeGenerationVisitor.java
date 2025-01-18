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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerationVisitor implements Visitor {

    // T: This variable is used distinguish the generation of code for VarDecl in the case we are in globalScope
    // and in the case we aren't in globalScope. In the case we are in globalScope, the VarDecl must be initialized
    // by a function called by main. So we need to divide declaration from initialization.
    // T: WARNING change the name of this variable
    public boolean globalScope;

    public CodeGenerationVisitor() {
        globalScope = false;
    }

    public static String globalVariableInitFunction = "initializeGlobalVariable_()";

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
            return " (" + OperatorConverter.plusStringOperator + "(" + leftExpression + "," + rightExpression + ")" + " )";
        }
        // T: In the case we had Div operator we must ensure that the second parameter of division is casted
        // to double to ensure that the result is calculated as a double.
        else if(node.op == BinaryOpNode.Type.Div && resultType == Type.Double) {
            return " (" + leftExpression + " /(double) (" + rightExpression + ")" + " )";
        }

        return " (" + leftExpression + " " + operator + " " + rightExpression + " )";
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

        return " (" + op + " " + argExpression + ") ";
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
                line = "*" + identifierString + " = *" + OperatorConverter.cloneString + "(" + exprOpNodeString + ");";
            }

            assignments.add(line);
        }

        return assignments;
    }

    @Override
    // T: WARNING we expect that all the identifier that are
    // reference are written adding '*'(excluding the String)
    public Object visit(CallOpNode node) {

        // T: Unused
        node.identifierNode.accept(this);

        ScopeEntry scopeEntry = node.scope.findProc(node.identifierNode.identifier);

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

        String semi = "";
        if(node.isStatement)
            semi = ";";

        return node.identifierNode.identifier + OperatorConverter.addonForFunctionIdentifier + "(" + parametersString.toString() + ")" + semi;
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

        List<String> lines = new ArrayList<>();

        for(var identifierNode : node.identifierNodes) {
            String identifierString = (String)identifierNode.accept(this);

            ScopeEntry scopeEntry = node.scope.findVar(identifierString);

            // T: we don't need the & in the case in wich we are using a string(char**)
            if(scopeEntry.varType != Type.String) {
                identifierString = "&" + identifierString;
            }

            String pattern = OperatorConverter.pattern(scopeEntry.varType);

            String inputLine = "scanf( \"" + pattern + "\" , " + identifierString + ");";
            if(scopeEntry.varType == Type.String) {
                inputLine = OperatorConverter.inputString + "(" + identifierString + ");";
            }

            lines.add(inputLine);
        }

        return lines;
    }

    @Override
    public Object visit(ReturnOpNode node) {
        String identifierString = (String)node.exprOpNode.accept(this);

        if(node.type == Type.String) {
            return "return " + OperatorConverter.cloneString + "(" + identifierString + ");";
        }

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
            newLine = "\\n";

        StringBuilder regexForPrintf = new StringBuilder("");
        StringBuilder parameters = new StringBuilder("");

        String comma = "";
        for(var exprOpNode : node.exprOpNodes) {
            String exprOpNodeString = (String)exprOpNode.accept(this);

            regexForPrintf.append(OperatorConverter.pattern(exprOpNode.type));
            if(exprOpNode.type == Type.String) {
                exprOpNodeString = " *" + exprOpNodeString;
            }
            exprOpNodeString = comma + exprOpNodeString;
            parameters.append(exprOpNodeString);

            comma = ",";
        }

        return "printf( \"" + regexForPrintf.toString() + newLine + "\" , " + parameters.toString() + ");";
    }

    @Override
    public Object visit(BeginEndOpNode node) {

        List<String> lines = new ArrayList<>();

        lines.add("int main()");
        lines.add("{");
        lines.add(globalVariableInitFunction + ";");

        for(var varDeclOpNode : node.varDeclOpNodes) {
            String varDeclOpLine = (String)varDeclOpNode.accept(this);
            lines.add(varDeclOpLine);
        }

        for(var statOpNode : node.statOpNodes) {
            Object result = statOpNode.accept(this);


            if(result instanceof String) {
                String stringStatOpNode = (String)result;
                lines.add(stringStatOpNode);
            } else {
                List<String> statOpNodeLines = (List)result;
                lines.addAll(statOpNodeLines);
            }
        }

        lines.add("return 0;");
        lines.add("}");

        return lines;
    }

    @Override
    public Object visit(BodyOpNode node) {

        List<String> lines = new ArrayList<>();

        for(var varDeclOpNode : node.varDeclOpNodes) {
            String varDeclOpLine = (String)varDeclOpNode.accept(this);
            lines.add(varDeclOpLine);
        }

        for(var statOpNode : node.statOpNodes) {
            Object result = statOpNode.accept(this);
            if(result instanceof String) {
                String stringStatOpNode = (String)result;
                lines.add(stringStatOpNode);
            } else {
                List<String> statOpNodeLines = (List)result;
                lines.addAll(statOpNodeLines);
            }
        }

        return lines;
    }

    @Override
    // T: to handle the string constant, wrap the value around a cloneString function
    public Object visit(ConstantNode node) {

        if(node.type == Type.String) {

            String valueString = node.value.replace("\n", "\\n").
                    replace("\t", "\\t").
                    replace("\r", "\\r");

            return OperatorConverter.fromConstantToHeap + "( \"" + valueString + "\" )";
        }
        else if(node.type == Type.Boolean) {
            if(node.value.equals("true")) {
                return "1";
            }
            // T: if there isn't 'true', must be 'false'
            return "0";
        }

        return node.value;
    }

    @Override
    public Object visit(DefDeclOpNode node) {

        List<String> lines = new ArrayList<>();
        StringBuilder headerLine = new StringBuilder("");

        // T: create header of the function/procedure (START)

        // T: unused
        node.identifierNode.accept(this);

        String typeNodeString = (String)node.typeNode.accept(this);

        headerLine.append(typeNodeString + " ");
        headerLine.append(node.identifierNode.identifier + OperatorConverter.addonForFunctionIdentifier + "( ");

        String comma = "";
        for(var parDeclOpNode : node.parDeclOpNodes) {
            String parDeclOpNodeString = (String)parDeclOpNode.accept(this);
            headerLine.append(comma + parDeclOpNodeString);

            comma = ",";
        }

        headerLine.append(" )");

        lines.add(headerLine.toString());
        // T: create header of the function/procedure (END)

        // T: create the body (START)
        lines.add("{");
        List<String> bodyLines = (List)node.bodyOpNode.accept(this);
        lines.addAll(bodyLines);
        lines.add("}");
        // T: create the body (END)

        return lines;
    }

    @Override
    // T: The result of this function is always discarded when the
    // identifier is referred to a function(CallOpNode, DefDeclOpNode).
    // T: This function return:
    // !function & string & ref     -> identifier
    // !function & string & !ref    -> identifier
    // !function & !string & ref    -> "*" + identifier
    // !function & !string & !ref   -> identifier
    // function                     -> identifier
    public Object visit(IdentifierNode node) {

        // T: we assume that we want to use this visit
        ScopeEntry scopeEntry = node.scope.findVar(node.identifier);

        if(scopeEntry == null) {
            return node.identifier;
        }

        if(scopeEntry.kind == ScopeEntry.Kind.Var) {
            // T: string & ref -> identifier
            // T: string & !ref -> identifier
            // !string & ref -> *identifier
            // !string & !ref -> identifier

            if(scopeEntry.varType != Type.String && scopeEntry.ref) {
                return "*" + node.identifier;
            }
            return node.identifier;

        }
        else {
            return node.identifier;
        }
    }

    @Override
    public Object visit(ParDeclOpNode node) {

        StringBuilder parDeclOpNodeLine = new StringBuilder("");

        String typeNodeString = (String)node.typeNode.accept(this);

        String comma = "";
        for(var pVarOpNode : node.pVarOpNodes) {
            String pVarOpNodeString = (String)pVarOpNode.accept(this);

            parDeclOpNodeLine.append(comma + typeNodeString + " " + pVarOpNodeString);

            comma = ",";
        }

        return parDeclOpNodeLine.toString();
    }

    @Override
    public Object visit(ProgramOpNode node) {

        List<String> lines = new ArrayList<>();

        // T: import code from lib.c (START)
        try {
            lines.add("/*STANDARD IMPORT*/");
            List<String> libLines = Files.readAllLines(Paths.get("codeToImport" + File.separator + "lib.c"));
            lines.addAll(libLines);
            lines.add("/*STANDARD IMPORT*/");
            lines.add("\n\n\n");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // T: import code from lib.c (END)




        // T: Put in different collection declarations and definitions of functions and global variables (START)
        List<String> functionDeclarations = new ArrayList<>();
        functionDeclarations.add(OperatorConverter.commentForFunctionDeclarationSection);
        List<String> functionDefinitions = new ArrayList<>();
        functionDefinitions.add(OperatorConverter.commentForFunctionDefinitionSection);

        List<String> globVariableDeclarations = new ArrayList<>();
        globVariableDeclarations.add(OperatorConverter.commentForGlobVarDeclarationSection);
        List<String> globVariablesDefinition = new ArrayList<>();
        globVariablesDefinition.add(OperatorConverter.commentForGlobVarDefinitionSection);
        globVariablesDefinition.add("void " + globalVariableInitFunction);
        globVariablesDefinition.add("{");

        for(var declNode : node.declsNodes) {

            if(declNode instanceof VarDeclOpNode) {
                globalScope = true;
                List<String> declAndDef = (List)declNode.accept(this);
                globalScope = false;

                globVariableDeclarations.add(declAndDef.get(0));
                globVariablesDefinition.add(declAndDef.get(1));
            }
            else {
                List<String> functionDefinitionLines = (List)declNode.accept(this);

                // T: extract from the definition of the function the header of the function
                functionDeclarations.add(functionDefinitionLines.get(0) + ";");

                functionDefinitions.addAll((List)functionDefinitionLines);
            }
        }

        globVariablesDefinition.add("}");

        functionDeclarations.add(OperatorConverter.commentForFunctionDeclarationSection);
        functionDefinitions.add(OperatorConverter.commentForFunctionDefinitionSection);
        globVariableDeclarations.add(OperatorConverter.commentForGlobVarDeclarationSection);
        globVariablesDefinition.add(OperatorConverter.commentForGlobVarDefinitionSection);
        // T: Put in different collection declarations and definitions of functions and global variables (END)



        // T: combine in the correct way definitions and declarations of function and global variables (START)
        lines.addAll(functionDeclarations);
        lines.addAll(globVariableDeclarations);
        lines.addAll(functionDefinitions);
        lines.addAll(globVariablesDefinition);
        // T: combine in the correct way definitions and declarations of function and global variables (END)




        List<String> beginEndOpNodeLines = (List)node.beginEndOpNode.accept(this);
        lines.addAll(beginEndOpNodeLines);

        return lines;
    }

    @Override
    public Object visit(PVarOpNode node) {

        // T: unused
        node.identifierNode.accept(this);

        ScopeEntry scopeEntry = node.scope.findVar(node.identifierNode.identifier);

        Type typeVar = scopeEntry.varType;
        if(typeVar != Type.String) {
            if(scopeEntry.ref) {
                return "*" + node.identifierNode.identifier;
            }
            else {
                return node.identifierNode.identifier;
            }
        }
        else {
            return node.identifierNode.identifier;
        }
    }

    @Override
    public Object visit(TypeNode node) {
        return OperatorConverter.convertTypeInC(node.type);
    }

    @Override
    public Object visit(TypeOrConstantNode node) {

        if(node.typeNode != null) {
            return node.typeNode.accept(this);
        }

        if(node.constantNode != null) {
            return node.constantNode.accept(this);
        }

        return null;
    }



    @Override
    // T: This function create definitions for global/non global variable
    // on the base of the globalScope field.
    public Object visit(VarDeclOpNode node) {

        Object result = node.typeOrConstant.accept(this);

        Type type = node.typeOrConstant.type;
        String typeString = OperatorConverter.convertTypeInC(type);

        // T: if the constant node isn't null, we take this value and
        // use that to initilize the variables.
        String value = null;
        if(node.typeOrConstant.constantNode != null) {
            value = (String)result;
        }

        // T: In the case the definition isn't global (START)
        if(! globalScope) {
            StringBuilder varDeclOpNodeLine = new StringBuilder("");

            varDeclOpNodeLine.append(typeString + " ");

            String comma = "";
            for(var varOptInitOpNode : node.varOptInitOpNodes) {
                String varOptInitOpNodeString = (String)varOptInitOpNode.accept(this);
                // T: add the value of CostantNode to initialize the variable
                if(node.typeOrConstant.constantNode != null) {
                    varOptInitOpNodeString = varOptInitOpNodeString + " = " + value;
                }
                // T: In the case no initial value is provided, assign default values for the types
                else if(varOptInitOpNode.exprOpNode == null) {
//                    if(node.typeOrConstant.type == Type.String) {
//                        varOptInitOpNodeString = varOptInitOpNodeString + " = (char**)malloc(sizeof(char*))";
//                    }

                    varOptInitOpNodeString = varOptInitOpNodeString + " = " + OperatorConverter.defaultValueForType(node.typeOrConstant.type);
                }

                varDeclOpNodeLine.append(comma + " " + varOptInitOpNodeString);

                comma = ",";
                if(type == Type.String) {
                    comma = ",**";
                }
            }

            varDeclOpNodeLine.append(";");

            return varDeclOpNodeLine.toString();
        }
        // T: In the case the definition isn't global (END)

        // T: In the case the definition is global (START)
        // T: In this case we store declarations and definitions like a
        // list formed by two string(first string for declaration, second for definitions).
        else {
            List<String> lines = new ArrayList<>();
            StringBuilder declarations = new StringBuilder("");
            declarations.append(typeString + " ");
            StringBuilder definitions = new StringBuilder("");

            String comma = "";
            for(var varOptInitOpNode : node.varOptInitOpNodes) {
                String varOptInitOpNodeString = (String)varOptInitOpNode.accept(this);
                // T: in the case the ConstantNode is provided, no expression value is provided
                // so we use this value to initialize the variable
                if(node.typeOrConstant.constantNode != null) {
                    definitions.append(varOptInitOpNodeString + " = " + value + ";");
                }
                // T: In the case no constant is provided, we try to use the exprOpNode value
                else if(varOptInitOpNode.exprOpNode != null) {
                    definitions.append(varOptInitOpNodeString + ";");
                }
                // T: In the case no initial value is provided, assign default values for the types
                else {
//                    if(node.typeOrConstant.type == Type.String) {
//                        definitions.append(varOptInitOpNodeString + " = (char**)malloc(sizeof(char*));");
//                    }
                    definitions.append(varOptInitOpNodeString + " = " + OperatorConverter.defaultValueForType(node.typeOrConstant.type) + ";");
                }

                declarations.append(comma + varOptInitOpNode.identifierNode.identifier);

                comma = ",";
                if(type == Type.String) {
                    comma = ",**";
                }
            }

            declarations.append(";\n");

            lines.add(declarations.toString());
            lines.add(definitions.toString());
            return lines;
        }
        // T: In the case the definition is global (END)
    }

    @Override
    // T: This function return only the identifier if no expression is provided
    // to initialize the identifier.
    public Object visit(VarOptInitOpNode node) {

        // T: unused
        node.identifierNode.accept(this);

        if(node.exprOpNode != null) {
            String exprOpNodeString = (String)node.exprOpNode.accept(this);
            if(node.exprOpNode.type == Type.String && node.exprOpNode instanceof ExprValueNode) {
                ExprValueNode exprOpNode = (ExprValueNode)node.exprOpNode;

                if(exprOpNode.identifierNode != null) {
                    return node.identifierNode.identifier + " = " + OperatorConverter.cloneString + "(" + exprOpNodeString + ")";
                }
            }

            return node.identifierNode.identifier + " = " + exprOpNodeString;
        }

        return node.identifierNode.identifier;
    }
}
