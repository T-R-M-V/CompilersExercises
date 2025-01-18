package org.example.type;

import org.example.Type;
import org.example.tree.expr.BinaryOpNode;
import org.example.tree.expr.UnaryOpNode;

import java.util.List;

public class TypeSystemOperator {

    public static Type[][][] typeSystemBinaryOperator;
    public static Type[][] typeSystemUnaryOperator;

    static {
        // T: type system for binary operators (START)
        typeSystemBinaryOperator = new Type[BinaryOpNode.Type.values().length][Type.values().length][Type.values().length];

        for(int i = 0; i < BinaryOpNode.Type.values().length; i++) {
            for(int j = 0; j < Type.values().length; j++) {
                for(int k = 0; k < Type.values().length; k++) {
                    typeSystemBinaryOperator[i][j][k] = Type.Error;
                }
            }
        }

        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Integer;
        typeSystemBinaryOperator[BinaryOpNode.Type.Minus.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Integer;
        typeSystemBinaryOperator[BinaryOpNode.Type.Times.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Integer;
        typeSystemBinaryOperator[BinaryOpNode.Type.Div.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Integer;

        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Minus.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Times.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Div.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Double;

        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Minus.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Times.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Div.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Double;

        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Minus.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Times.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Double;
        typeSystemBinaryOperator[BinaryOpNode.Type.Div.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Double;

        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.String.ordinal()][Type.String.ordinal()] = Type.String;
        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.Integer.ordinal()][Type.String.ordinal()] = Type.String;
        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.String.ordinal()][Type.Integer.ordinal()] = Type.String;
        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.Double.ordinal()][Type.String.ordinal()] = Type.String;
        typeSystemBinaryOperator[BinaryOpNode.Type.Plus.ordinal()][Type.String.ordinal()][Type.Double.ordinal()] = Type.String;
        
        typeSystemBinaryOperator[BinaryOpNode.Type.And.ordinal()][Type.Boolean.ordinal()][Type.Boolean.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Or.ordinal()][Type.Boolean.ordinal()][Type.Boolean.ordinal()] = Type.Boolean;

        typeSystemBinaryOperator[BinaryOpNode.Type.Eq.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ne.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Lt.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Le.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Gt.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ge.ordinal()][Type.Integer.ordinal()][Type.Integer.ordinal()] = Type.Boolean;

        typeSystemBinaryOperator[BinaryOpNode.Type.Eq.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ne.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Lt.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Le.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Gt.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ge.ordinal()][Type.Double.ordinal()][Type.Double.ordinal()] = Type.Boolean;

        typeSystemBinaryOperator[BinaryOpNode.Type.Eq.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ne.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Lt.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Le.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Gt.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ge.ordinal()][Type.Integer.ordinal()][Type.Double.ordinal()] = Type.Boolean;

        typeSystemBinaryOperator[BinaryOpNode.Type.Eq.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ne.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Lt.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Le.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Gt.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ge.ordinal()][Type.Double.ordinal()][Type.Integer.ordinal()] = Type.Boolean;

        typeSystemBinaryOperator[BinaryOpNode.Type.Eq.ordinal()][Type.String.ordinal()][Type.String.ordinal()] = Type.Boolean;
        typeSystemBinaryOperator[BinaryOpNode.Type.Ne.ordinal()][Type.String.ordinal()][Type.String.ordinal()] = Type.Boolean;
        // T: type system for binary operators (END)



        // T: type system for unary operators (START)
        typeSystemUnaryOperator = new Type[UnaryOpNode.Type.values().length][Type.values().length];

        for(int i = 0; i < UnaryOpNode.Type.values().length; i++) {
            for(int j = 0; j < Type.values().length; j++) {
                typeSystemUnaryOperator[i][j] = Type.Error;
            }
        }

        typeSystemUnaryOperator[UnaryOpNode.Type.Uminus.ordinal()][Type.Integer.ordinal()] = Type.Integer;
        typeSystemUnaryOperator[UnaryOpNode.Type.Uminus.ordinal()][Type.Double.ordinal()] = Type.Double;

        typeSystemUnaryOperator[UnaryOpNode.Type.Not.ordinal()][Type.Boolean.ordinal()] = Type.Boolean;
        // T: type system for unary operators (END)
    }

    public static Type getTypeUnaryOperator(UnaryOpNode.Type op, Type arg) {
        return typeSystemUnaryOperator[op.ordinal()][arg.ordinal()];
    }

    public static Type getTypeBinaryOperator(BinaryOpNode.Type op, Type arg1, Type arg2) {
        return typeSystemBinaryOperator[op.ordinal()][arg1.ordinal()][arg2.ordinal()];
    }
}
