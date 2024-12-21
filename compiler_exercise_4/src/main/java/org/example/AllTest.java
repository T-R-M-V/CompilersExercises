package org.example;

import java_cup.runtime.Symbol;
import org.example.tree.ProgramOpNode;
import org.w3c.dom.Document;

import java.io.*;
import java.util.HashMap;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Circuit {
    public static void main(String[] args) throws Exception {

        HashMap<String, String> testResults = new HashMap<>();

        String testFolder = "test_files";
        File testFolderFile = new File(testFolder);

        if (testFolderFile.exists() && testFolderFile.isDirectory()) {
            File[] files = testFolderFile.listFiles();

            if (files != null && files.length > 0) {
                for(var file: files) {
                    if(file.isFile()) {
                        String res = "corretta!!";
                        FileReader reader = new FileReader(file);
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

                        System.out.println("Frase: " + file.getName() + " " + res);
                        testResults.put(file.getName(), res);
                    }
                }
            }
        }

        testResults.entrySet().forEach((e) -> {System.out.println(e.getKey() + ": " + e.getValue());} );
    }
}
