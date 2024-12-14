package org.example.tree;

import org.example.Visitor;

public abstract class Node {
    public abstract Object accept(Visitor v);
}
