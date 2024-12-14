package org.example;

import org.example.tree.*;
import org.example.tree.expr.BinaryOpNode;
import org.example.tree.expr.ExprOpNode;
import org.example.tree.expr.ExprValueNode;
import org.example.tree.expr.UnaryOpNode;
import org.example.tree.statements.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PrintASTVisitor implements Visitor {

    public PrintASTVisitor() {}

    // For expressions (START)
    @Override
    public Object visit(BinaryOpNode node) {
        return null;
    }

    @Override
    public Object visit(ExprOpNode node) {
        return null;
    }

    @Override
    public Object visit(ExprValueNode node) {
        return null;
    }

    @Override
    public Object visit(UnaryOpNode node) {
        return null;
    }
    // For expressions (END)

    // For statements (START)
    @Override
    public Object visit(AssignOpNode node) {
        return null;
    }

    @Override
    public Object visit(CallOpNode node) {
        return null;
    }

    @Override
    public Object visit(IfThenElseOpNode node) {
        return null;
    }

    @Override
    public Object visit(IfThenOpNode node) {
        return null;
    }

    @Override
    public Object visit(ReadOpNode node) {
        return null;
    }

    @Override
    public Object visit(ReturnOpNode node) {
        return null;
    }

    @Override
    public Object visit(StatOpNode node) {
        return null;
    }

    @Override
    public Object visit(WhileOpNode node) {
        return null;
    }

    @Override
    public Object visit(WriteOpNode node) {
        return null;
    }
    // For statements (END)





    @Override
    public Object visit(BeginEndOpNode node) {
        return null;
    }

    @Override
    public Object visit(BodyOpNode node) {
        return null;
    }

    @Override
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
    public Object visit(ProgramOpNode programOpNode) {

        // T: create document (START)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Document document = builder.newDocument();
        // T: create document (END)

        // T: create and add programOpNode (START)
        Element programOpNodeXml = document.createElement("ProgramOpNode");
        document.appendChild(programOpNodeXml);
        programOpNodeXml.setAttribute("name", "Program");
        // T: create and add programOpNode (END)






        return document;
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
