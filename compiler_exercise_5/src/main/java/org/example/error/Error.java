package org.example.error;

import java.util.ArrayList;
import java.util.List;

// T: this class is used to take notes of errors during compilation (for now, only the semantic error)
public class Error {

    public Error(String message, int line, int column) {
        this.message = message;
        this.line = line;
        this.column = column;
    }

    public String message;
    public int line;
    public int column;

    public String toString() {
        return "line: " + line + " col: " + column + "    : " + message;
    }

    public static void launchError(String message, int line, int column) {
        stackError.add(new Error(message, line, column));
    }

    // T: the stack of error used during compilation
    public static List<Error> stackError = new ArrayList<>();
}
