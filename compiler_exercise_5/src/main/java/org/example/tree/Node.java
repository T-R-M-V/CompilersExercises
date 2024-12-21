package org.example.tree;

import org.example.Visitor;
import org.example.scope.Scope;

public abstract class Node {
    public abstract Object accept(Visitor v);

    public Scope scope;
}
