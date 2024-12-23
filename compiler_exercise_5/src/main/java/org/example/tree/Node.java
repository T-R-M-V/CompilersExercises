package org.example.tree;

import org.example.Visitor;
import org.example.scope.Scope;

public abstract class Node {
    public abstract Object accept(Visitor v);

    public Scope scope;

    // T: are used to specify the line and column where the code referred to this line start
    public int line;
    public int column; // T: the number of column is imprecise because we can't correctly count the number of character of a tab

    public void setStartPos(int line, int column) {
        this.line = line;
        this.column = column;
    }
}
