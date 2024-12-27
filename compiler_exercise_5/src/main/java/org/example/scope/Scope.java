package org.example.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Scope {

    public Scope() {
        scopeData = new HashMap<>();
        parent = null;

        this.startingLine = -1;
        this.startingColumn = -1;

        children = new ArrayList<>();
    }

    public void setStartPos(int startingLine, int startingColumn) {
        this.startingLine = startingLine;
        this.startingColumn = startingColumn;
    }

    public ScopeEntry findProc(String lexem) {
        ScopeKey scopeKey = new ScopeKey(lexem, ScopeEntry.Kind.Proc);

        Scope currentScope = this;
        ScopeEntry entry = null;
        while(currentScope != null) {
            entry = currentScope.scopeData.get(scopeKey);
            if(entry != null)
                break;
            currentScope = currentScope.parent;
        }

        return entry;
    }

    public ScopeEntry findVar(String lexem) {
        ScopeKey scopeKey = new ScopeKey(lexem, ScopeEntry.Kind.Var);

        Scope currentScope = this;
        ScopeEntry entry = null;
        while(currentScope != null) {
            entry = currentScope.scopeData.get(scopeKey);
            if(entry != null)
                break;
            currentScope = currentScope.parent;
        }

        return entry;
    }

    public HashMap<ScopeKey, ScopeEntry> scopeData;
    public Scope parent;
    public int startingLine, startingColumn;

    public void setParent(Scope parent) {
        this.parent = parent;

        // T: for debug purpose only
        parent.children.add(this);
    }

    // T: for debug purpose only
    public List<Scope> children;
}