package org.example;

import java_cup.runtime.Symbol;
import org.example.tree.ProgramOpNode;
import org.w3c.dom.Document;

import java.io.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Circuit {
    public static void main(String[] args) throws Exception {
        String res = "corretta!!";
        // System.out.println("Type in circuit, hit Return, then Cmd-D (in MacOs) o Ctrl-D (in Windows)");
        // InputStreamReader inp = new InputStreamReader(System.in);
        FileReader reader = new FileReader(args[0]);
        Reader keyboard = new BufferedReader(reader);
        parser p = new parser(new Lexer(keyboard));

        try {
            Symbol symbol = p.debug_parse(); // l'uso di p.debug_parse() al posto di p.parse() produce tutte le azioni del parser durante il riconoscimento
            ProgramOpNode programOpNode = (ProgramOpNode) symbol.value;

            PrintASTVisitor printASTVisitor = new PrintASTVisitor();
            Document document = (Document) printASTVisitor.visit(programOpNode);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("AST.xml"));
            transformer.transform(source, result);
        }
        catch(Exception e) {
            res = "errata!!";
            e.printStackTrace();
        }

        System.out.println("Frase " + res);
    }
}
