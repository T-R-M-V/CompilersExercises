package org.example;

import java_cup.runtime.Symbol;
import org.example.codeGeneration.CodeGenerationVisitor;
import org.example.scope.Scope;
import org.example.scope.ScopeVisitor;
import org.example.tree.ProgramOpNode;
import org.example.type.TypeCheckingVisitor;
import org.w3c.dom.Document;
import org.example.error.Error;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SingleTest {

    public static void main(String args[]) throws FileNotFoundException {

        String filename = args[0];
        FileReader reader = new FileReader(args[0]);
        Reader keyboard = new BufferedReader(reader);
        parser p = new parser(new Lexer(keyboard));

        int begin = filename.lastIndexOf('/');
        int end = filename.lastIndexOf(".");
        String outputCodeGenerationFile = "test_files/c_out/" + filename.substring(begin+1, end) + ".c";
        System.out.println(outputCodeGenerationFile);

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



            ScopeVisitor scopeVisitor = new ScopeVisitor();
            Scope scope = (Scope)scopeVisitor.visit(programOpNode);

            TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
            Type returnedType = (Type)typeCheckingVisitor.visit(programOpNode);

            if(returnedType != Type.Error) {
                CodeGenerationVisitor codeGenerationVisitor = new CodeGenerationVisitor();
                List<String> generatedCLines = (List)codeGenerationVisitor.visit(programOpNode);
                Files.write(Paths.get(outputCodeGenerationFile), generatedCLines);

                // T: write also in the file for testing with Clang (START)
//                String outputForDebugWithClang = "C:\\Languages\\CLang\\LLVM\\bin\\main.c";
//                Files.write(Paths.get(outputForDebugWithClang), generatedCLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                // T: write also in the file for testing with Clang (END)
            }
        }
        catch(Exception e) {
            System.out.println("Errors: ");
            System.out.println(e.getMessage());
            Error.printErrors();

            // e.printStackTrace();

            return;
        }

        Error.printErrors();
    }

}