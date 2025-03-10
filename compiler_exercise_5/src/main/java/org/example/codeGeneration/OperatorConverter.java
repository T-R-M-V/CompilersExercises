package org.example.codeGeneration;

import org.example.Type;
import org.example.tree.expr.BinaryOpNode;
import org.example.tree.expr.UnaryOpNode;

public class OperatorConverter {

    public static String plusStringOperator = "stringConcatenation_";
    public static String eqStringOperator = "equalForString_";
    public static String neStringOperator = "notEqualForString_";
    public static String integerToString = "fromIntegerToString_";
    public static String doubleToString = "fromDoubleToString_";
    public static String cloneString = "cloneString_";
    public static String fromConstantToHeap = "fromConstantStringToHeap_";
    public static String inputString = "inputString_";
    public static String addonForFunctionIdentifier = "_fun";

    public static String commentForFunctionDefinitionSection = "/* FUNCTION DEFINITIONS */";
    public static String commentForFunctionDeclarationSection = "/* FUNCTION DECLARATIONS */";
    public static String commentForGlobVarDeclarationSection = "/* GLOBAL VARIABLES DECLARATION */";
    public static String commentForGlobVarDefinitionSection = "/* GLOBAL VARIABLES DEFINITION */";
    public static String commentForGlobVarDefaultInitializationSection = "/* GLOBAL VARIABLES DEFAULT INITIALIZATIONS */";

    public static String convertInC(BinaryOpNode.Type op) {
        switch (op) {
            case Plus -> {
                return "+";
            }
            case Minus -> {
                return "-";
            }
            case Times -> {
                return "*";
            }
            case Div -> {
                return "/";
            }
            case And -> {
                return "&&";
            }
            case Or -> {
                return "||";
            }
            case Gt -> {
                return ">";
            }
            case Ge -> {
                return ">=";
            }
            case Lt -> {
                return "<";
            }
            case Le -> {
                return "<=";
            }
            case Eq -> {
                return "==";
            }
            case Ne -> {
                return "!=";
            }
            default -> {
                return null;
            }
        }
    }

    public static String convertInC(UnaryOpNode.Type op) {
        switch (op) {
            case Uminus -> {
                return "-";
            }
            case Not -> {
                return "!";
            }
            default -> {
                return null;
            }
        }
    }

    public static String pattern(Type type) {
        switch (type) {
            case Error -> {
                return null;
            }
            case Integer -> {
                return "%d";
            }
            // T: WARNING shit here!!!
            case Boolean -> {
                return "%d";
            }
            case Double -> {
                return "%lf";
            }
            case String -> {
                return "%s";
            }
            case Char -> {
                return "%c";
            }
            case Void -> {
                return null;
            }
            default -> {
                return null;
            }
        }
    }

    public static String convertTypeInC(Type type) {
        switch (type) {
            case Error -> {
                return null;
            }
            case Integer -> {
                return "int";
            }
            case Boolean -> {
                return "int/*ex boolean*/";
            }
            case Double -> {
                return "double";
            }
            case String -> {
                return "char**";
            }
            case Char -> {
                return "char";
            }
            case Void -> {
                return "void";
            }
            default -> {
                return null;
            }
        }
    }

    public static String defaultValueForType(Type type) {
        switch (type) {
            case Error -> {
                return null;
            }
            case Integer -> {
                return "0";
            }
            case Boolean -> {
                return "0";
            }
            case Double -> {
                return "0.0";
            }
            case String -> {
                return OperatorConverter.fromConstantToHeap + "(" + "\"\"" + ")";
            }
            case Char -> {
                return "'a'";
            }
            case Void -> {
                return null;
            }
        }

        return null;
    }
}
