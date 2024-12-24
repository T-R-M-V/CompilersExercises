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

    public PrintASTVisitor() {
        // T: create document (START)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        document = builder.newDocument();
        // T: create document (END)
    }


    // For expressions (START)
    @Override
    public Object visit(BinaryOpNode node) {
        Element binaryOpNodeXml = document.createElement("BinaryOpNode");

        Element leftExprOpNode = (Element) node.left.accept(this);
        binaryOpNodeXml.appendChild(leftExprOpNode);

        Element rightExprOpNode = (Element) node.right.accept(this);
        binaryOpNodeXml.appendChild(rightExprOpNode);

        binaryOpNodeXml.setAttribute("type", node.op + "");

        return binaryOpNodeXml;
    }

    @Override
    public Object visit(ExprOpNode node) {
        System.out.println("We don't use ExprOpNode!");
        return null;
    }

    @Override
    public Object visit(ExprValueNode node) {
        Element exprValueNodeXml = document.createElement("ExprValueNode");

        if(node.callOpNode != null)
        {
            Element typeNodeXml = (Element) node.callOpNode.accept(this);
            exprValueNodeXml.appendChild(typeNodeXml);
        }

        if(node.identifierNode != null)
        {
            Element typeNodeXml = (Element) node.identifierNode.accept(this);
            exprValueNodeXml.appendChild(typeNodeXml);
        }

        if(node.constantNode != null)
        {
            Element typeNodeXml = (Element) node.constantNode.accept(this);
            exprValueNodeXml.appendChild(typeNodeXml);
        }

        return exprValueNodeXml;
    }

    @Override
    public Object visit(UnaryOpNode node) {
        Element unaryOpNodeXml = document.createElement("UnaryOpNode");

        Element exprOpNode = (Element) node.exprOpNode.accept(this);
        unaryOpNodeXml.appendChild(exprOpNode);

        unaryOpNodeXml.setAttribute("type", node.op + "");

        return unaryOpNodeXml;
    }
    // For expressions (END)

    // For statements (START)
    @Override
    public Object visit(AssignOpNode node) {
        Element assignOpNodeXml = document.createElement("AssignOpNode");

        for(var identifierNode : node.identifierNodes) {
            Element identifierNodeXml = (Element)identifierNode.accept(this);
            assignOpNodeXml.appendChild(identifierNodeXml);
        }

        for(var exprOpNode: node.exprOpNodes) {
            Element exprOpNodeXml = (Element)exprOpNode.accept(this);
            assignOpNodeXml.appendChild(exprOpNodeXml);
        }

        return assignOpNodeXml;
    }

    @Override
    public Object visit(CallOpNode node) {

        Element callOpNodeXml = document.createElement("CallOpNode");

        Element identifierNodeXml = (Element)node.identifierNode.accept(this);
        callOpNodeXml.appendChild(identifierNodeXml);

        if(node.exprOpNodes != null){
            for(var exprOpNode: node.exprOpNodes) {
                Element exprOpNodeXml = (Element)exprOpNode.accept(this);
                callOpNodeXml.appendChild(exprOpNodeXml);
            }
        }

        return callOpNodeXml;
    }

    @Override
    public Object visit(IfThenElseOpNode node) {
        Element ifThenElseOpNodeXml = document.createElement("IfThenElseOpNode");

        Element exprOpNodeXml = (Element)node.exprOpNode.accept(this);
        ifThenElseOpNodeXml.appendChild(exprOpNodeXml);

        Element thenBodyOpNodeXml = (Element)node.thenBodyOpNode.accept(this);
        ifThenElseOpNodeXml.appendChild(thenBodyOpNodeXml);

        Element elseBodyOpNodeXml = (Element)node.elseBodyOpNode.accept(this);
        ifThenElseOpNodeXml.appendChild(elseBodyOpNodeXml);

        return ifThenElseOpNodeXml;
    }

    @Override
    public Object visit(IfThenOpNode node) {
        Element ifThenOpNodeXml = document.createElement("IfThenOpNode");

        Element exprOpNodeXml = (Element)node.exprOpNode.accept(this);
        ifThenOpNodeXml.appendChild(exprOpNodeXml);

        Element thenBodyOpNodeXml = (Element)node.bodyOpNode.accept(this);
        ifThenOpNodeXml.appendChild(thenBodyOpNodeXml);

        return ifThenOpNodeXml;
    }

    @Override
    public Object visit(ReadOpNode node) {

        Element readOpNodeXml = document.createElement("ReadOpNode");

        for(var identifierNode : node.identifierNodes) {
            Element identifierNodeXml = (Element) identifierNode.accept(this);
            readOpNodeXml.appendChild(identifierNodeXml);
        }

        return readOpNodeXml;
    }

    @Override
    public Object visit(ReturnOpNode node) {
        Element returnOpNodeXml = document.createElement("ReturnOpNode");

        Element exprOpNodeXml = (Element) node.exprOpNode.accept(this);
        returnOpNodeXml.appendChild(exprOpNodeXml);

        return returnOpNodeXml;
    }

    @Override
    public Object visit(StatOpNode node) {
        System.out.println("We don't use StatOpNode!");
        return null; // T: We don't use it
    }

    @Override
    public Object visit(WhileOpNode node) {

        Element whileOpNodeXml = document.createElement("WhileOpNode");

        Element exprOpNodeXml = (Element) node.exprOpNode.accept(this);
        whileOpNodeXml.appendChild(exprOpNodeXml);

        Element bodyOpNodeXml = (Element) node.bodyOpNode.accept(this);
        whileOpNodeXml.appendChild(bodyOpNodeXml);

        return whileOpNodeXml;
    }

    @Override
    public Object visit(WriteOpNode node) {
        Element writeOpNodeXml = document.createElement("WriteOpNode");

        writeOpNodeXml.setAttribute("newline", Boolean.toString(node.newLine));

        for(var exprOpNode: node.exprOpNodes) {
            Element exprOpNodeXml = (Element) exprOpNode.accept(this);
            writeOpNodeXml.appendChild(exprOpNodeXml);
        }

        return writeOpNodeXml;
    }
    // For statements (END)





    @Override
    public Object visit(BeginEndOpNode node) {
        Element beginEndOpNodeXml = document.createElement("beginEndOpNode");

        for(var varDeclOpNode : node.varDeclOpNodes) {
            Element varDeclOpNodeXml = (Element)varDeclOpNode.accept(this);
            beginEndOpNodeXml.appendChild(varDeclOpNodeXml);
        }

        for(var statOpNode: node.statOpNodes) {
            Element statOpNodeXml = (Element)statOpNode.accept(this);
            beginEndOpNodeXml.appendChild(statOpNodeXml);
        }

        return beginEndOpNodeXml;
    }

    @Override
    public Object visit(BodyOpNode node) {

        Element bodyOpNodeXml = document.createElement("body");

        for(var varDeclOpNode : node.varDeclOpNodes) {
            Element varDeclOpNodeXml = (Element)varDeclOpNode.accept(this);
            bodyOpNodeXml.appendChild(varDeclOpNodeXml);
        }

        for(var statOpNode: node.statOpNodes) {
            Element statOpNodeXml = (Element)statOpNode.accept(this);
            bodyOpNodeXml.appendChild(statOpNodeXml);
        }

        return bodyOpNodeXml;
    }

    @Override
    public Object visit(ConstantNode node) {
        Element constantNodeXml = document.createElement("ConstantNode");

        constantNodeXml.setAttribute("type", node.type.toString());
        constantNodeXml.setAttribute("value", node.value);

        return constantNodeXml;
    }

    @Override
    public Object visit(DefDeclOpNode node) {

        Element defDeclOpNodeXml = document.createElement("DefDeclOpNode");

        Element identifierNodeXml = (Element) node.identifierNode.accept(this);
        defDeclOpNodeXml.appendChild(identifierNodeXml);

        for(var parDeclOpNode : node.parDeclOpNodes) {
            Element parDeclOpNodeXml = (Element) parDeclOpNode.accept(this);
            defDeclOpNodeXml.appendChild(parDeclOpNodeXml);
        }

        Element typeNodeXml = (Element) node.typeNode.accept(this);
        defDeclOpNodeXml.appendChild(typeNodeXml);

        Element bodyNodeXml = (Element) node.bodyOpNode.accept(this);
        defDeclOpNodeXml.appendChild(bodyNodeXml);

        return defDeclOpNodeXml;
    }

    @Override
    public Object visit(IdentifierNode node) {
        Element identifierNodeXml = document.createElement("IdentifierNode");

        identifierNodeXml.setAttribute("identifier", node.identifier);

        return identifierNodeXml;
    }

    // To-continue
    @Override
    public Object visit(ParDeclOpNode node) {
        Element parDeclOpNodeXml = document.createElement("ParDeclOpNode");

        for(var pVarOpNode : node.pVarOpNodes) {
            Element pVarOpNodeXml = (Element) pVarOpNode.accept(this);
            parDeclOpNodeXml.appendChild(pVarOpNodeXml);
        }

        Element typeNodeXml = (Element) node.typeNode.accept(this);
        parDeclOpNodeXml.appendChild(typeNodeXml);

        return parDeclOpNodeXml;
    }

    @Override
    public Object visit(ProgramOpNode programOpNode) {

        // T: create and add programOpNode (START)
        Element programOpNodeXml = document.createElement("ProgramOpNode");
        document.appendChild(programOpNodeXml);
        // T: create and add programOpNode (END)



        // T: create and add declaration elements (START)
        for(var node: programOpNode.declsNodes) {
            Element element = (Element) node.accept(this);
            programOpNodeXml.appendChild(element);
        }
        // T: create and add declaration elements (END)

        Element element = (Element) programOpNode.beginEndOpNode.accept(this);
        programOpNodeXml.appendChild(element);

        return document;
    }

    @Override
    public Object visit(PVarOpNode node) {
        Element pVarOpNodeXml = document.createElement("PVarOpNode");

        Element identifierNode = (Element) node.identifierNode.accept(this);
        pVarOpNodeXml.appendChild(identifierNode);

        pVarOpNodeXml.setAttribute("ref", Boolean.toString(node.ref));

        pVarOpNodeXml.appendChild(identifierNode);

        return pVarOpNodeXml;
    }

    @Override
    public Object visit(TypeNode node) {
        Element typeNodeXml = document.createElement("TypeNode");

        typeNodeXml.setAttribute("Type", node.type.toString());

        return typeNodeXml;
    }

    @Override
    public Object visit(TypeOrConstantNode node) {
        Element typeOrConstantNodeXml = document.createElement("typeOrConstantNode");

        if(node.typeNode != null) {
            Element typeNodeXml = (Element) node.typeNode.accept(this);
            typeOrConstantNodeXml.appendChild(typeNodeXml);
        }

        if(node.constantNode != null) {
            Element constantNodeXml = (Element) node.constantNode.accept(this);
            typeOrConstantNodeXml.appendChild(constantNodeXml);
        }

        return typeOrConstantNodeXml;
    }

    @Override
    public Object visit(VarDeclOpNode node) {
        Element varDeclOpXml = document.createElement("VarDeclOpNode");

        for(var varOptInitOpNode : node.varOptInitOpNodes) {
            Element pVarOpNodeXml = (Element) varOptInitOpNode.accept(this);
            varDeclOpXml.appendChild(pVarOpNodeXml);
        }

        Element typeOrConstantXml = (Element) node.typeOrConstant.accept(this);
        varDeclOpXml.appendChild(typeOrConstantXml);

        return varDeclOpXml;
    }

    @Override
    public Object visit(VarOptInitOpNode node) {
        Element varOptInitOpNodeXml = document.createElement("VarOptInitOpNode");

        Element identifierNodeXml = (Element) node.identifierNode.accept(this);
        varOptInitOpNodeXml.appendChild(identifierNodeXml);

        if(node.exprOpNode != null) {
            Element exprOpNodeXml = (Element) node.exprOpNode.accept(this);
            varOptInitOpNodeXml.appendChild(exprOpNodeXml);
        }

        return varOptInitOpNodeXml;
    }



    private Document document;
}
