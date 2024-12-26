package org.example;

import java_cup.runtime.Symbol;
import org.example.scope.Scope;
import org.example.scope.ScopeVisitor;
import org.example.tree.ProgramOpNode;
import org.example.error.Error;
import org.w3c.dom.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class AllTest {
    public static void main(String[] args) throws Exception {

        HashMap<String, String> testResults = new HashMap<>();
        HashMap<String, List<Error>> errors = new HashMap<>();

        String testFolder = "test_files";
        File testFolderFile = new File(testFolder);

        if (testFolderFile.exists() && testFolderFile.isDirectory()) {
            File[] files = testFolderFile.listFiles();

            if (files != null && files.length > 0) {
                for(var file: files) {

                    // T: reset list of errors
                    Error.stackError = new ArrayList<>();

                    if(file.isFile()) {
                        String res = "corretta!!";
                        FileReader reader = new FileReader(file);
                        Reader keyboard = new BufferedReader(reader);
                        parser p = new parser(new Lexer(keyboard));

                        try {
                            Symbol symbol = p.debug_parse(); // l'uso di p.debug_parse() al posto di p.parse() produce tutte le azioni del parser durante il riconoscimento
                            // Symbol symbol = p.parse();
                            ProgramOpNode programOpNode = (ProgramOpNode) symbol.value;

                            PrintASTVisitor printASTVisitor = new PrintASTVisitor();
                            Document document = (Document) printASTVisitor.visit(programOpNode);

                            Transformer transformer = TransformerFactory.newInstance().newTransformer();
                            transformer.setOutputProperty("indent", "yes");
                            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                            DOMSource source = new DOMSource(document);
                            StreamResult result = new StreamResult(new File("AST.xml"));
                            transformer.transform(source, result);



                            ScopeVisitor scopeVisitor = new ScopeVisitor();
                            Scope scope = (Scope)scopeVisitor.visit(programOpNode);
                        }
                        catch(Exception e) {
                            res = "errata!!";
                            e.printStackTrace();
                        }


                        System.out.println("Test: " + file.getName());
                        Error.printErrors();
                        System.out.println("\n");
                    }
                }
            }
        }

    }
}
